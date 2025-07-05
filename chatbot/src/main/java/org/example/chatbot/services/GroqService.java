package org.example.chatbot.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class GroqService {

    private final WebClient groqWebClient;

    @Value("${groq.api.model}")
    private String model;

    public GroqService(@Qualifier("groqWebClient") WebClient groqWebClient) {
        this.groqWebClient = groqWebClient;
    }

    public List<Object> cargarToolsDesdeArchivo() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            InputStream inputStream = new ClassPathResource("tools.json").getInputStream();
            return mapper.readValue(inputStream, List.class);
        } catch (IOException e) {
            throw new RuntimeException("No se pudo cargar tools.json", e);
        }
    }

    public Mono<String> procesarConsulta(String preguntaUsuario) throws JsonProcessingException {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", model);
        requestBody.put("messages", List.of(
                Map.of("role", "user", "content", preguntaUsuario)
        ));

        List<Object> tools = cargarToolsDesdeArchivo();
        requestBody.put("tools", tools);

        return groqWebClient.post()
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(JsonNode.class)
                .flatMap(response -> {
                    System.out.println("response \n" + response);
                    JsonNode toolCalls = response.path("choices").get(0).path("message").path("tool_calls");
                    if (toolCalls.isArray() && !toolCalls.isEmpty()) {
                        JsonNode functionCall = toolCalls.get(0).path("function");
                        String name = functionCall.path("name").asText();

                        ObjectMapper mapper = new ObjectMapper();
                        JsonNode arguments;
                        try {
                            arguments = mapper.readTree(functionCall.path("arguments").asText());
                        } catch (JsonProcessingException e) {
                            throw new RuntimeException(e);
                        }

                        if ("getViajes".equals(name)) {
                            return WebClient.create()
                                    .get()
                                    .uri("http://localhost:8003/viajes/historial")
                                    .retrieve()
                                    .bodyToMono(String.class)
                                    .flatMap(viajes -> consultarGroqConHistorial(viajes, preguntaUsuario));
                        } else if ("getParadas".equals(name)) {

                            return WebClient.create()
                                    .get()
                                    .uri("http://localhost:8008/paradas")
                                    .retrieve()
                                    .bodyToMono(String.class)
                                    .flatMap(paradas -> consultarGroqConHistorial(paradas, preguntaUsuario));
                        } else if ("getMonopatines".equals(name)) {

                            return WebClient.create()
                                    .get()
                                    .uri("http://localhost:8007/monopatines")
                                    .retrieve()
                                    .bodyToMono(String.class)
                                    .flatMap(monopatines -> consultarGroqConHistorial(monopatines, preguntaUsuario));

                        } else if("getFacturacion".equals(name)) {

                            return WebClient.create()
                                    .get()
                                    .uri("http://localhost:8005/facturacion")
                                    .retrieve()
                                    .bodyToMono(String.class)
                                    .flatMap(facturas -> consultarGroqConHistorial(facturas, preguntaUsuario));
                        }
                    }

                    // Si no hay función, devolvemos la respuesta generada
                    return Mono.just(
                            response.path("choices").get(0).path("message").path("content").asText()
                    );
                });
    }

    private Mono<String> consultarGroqConHistorial(String historial, String preguntaUsuario) {
        String prompt = """
                teniendo en cuenta estos datos:
                %s
                
                Pregunta: %s
                """.formatted(historial, preguntaUsuario);

        Map<String, Object> groqRequest = new HashMap<>();
        groqRequest.put("model", model);
        groqRequest.put("messages", List.of(
                Map.of("role", "user", "content", prompt)
        ));

        return groqWebClient.post()
                .bodyValue(groqRequest)
                .retrieve()
                .bodyToMono(JsonNode.class)
                .map(responseJson -> {
                    JsonNode contentNode = responseJson
                            .path("choices")
                            .get(0)
                            .path("message")
                            .path("content");
                    return contentNode.asText();
                });
    }
}
