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
                    if (toolCalls.isArray() && toolCalls.size() > 0) {
                        JsonNode functionCall = toolCalls.get(0).path("function");
                        String name = functionCall.path("name").asText();

                        ObjectMapper mapper = new ObjectMapper();
                        JsonNode arguments;
                        try {
                            arguments = mapper.readTree(functionCall.path("arguments").asText());
                        } catch (JsonProcessingException e) {
                            throw new RuntimeException(e);
                        }

                        if ("obtenerHistorialViajes".equals(name)) {
                            Long idUsuario = arguments.path("idUsuario").asLong();
                            String fechaDesde = arguments.path("fechaDesde").asText();
                            String fechaHasta = arguments.path("fechaHasta").asText();

                            return WebClient.create()
                                    .get()
                                    .uri("http://localhost:8003/viajes/historial?idUsuario={idUsuario}&fechaDesde={fechaDesde}&fechaHasta={fechaHasta}",
                                            idUsuario, fechaDesde, fechaHasta)
                                    .retrieve()
                                    .bodyToMono(String.class);
                        }
                        else if ("calcularDistanciaParadas".equals(name)) {
                            int parada1Id = arguments.path("parada1Id").asInt();
                            int parada2Id = arguments.path("parada2Id").asInt();

                            return WebClient.create()
                                    .get()
                                    .uri("http://localhost:8008/paradas/distancia?parada1Id={parada1Id}&parada2Id={parada2Id}",
                                            parada1Id, parada2Id)
                                    .retrieve()
                                    .bodyToMono(String.class);
                        }
                    }

                    // Si no hay función, devolvemos la respuesta generada
                    return Mono.just(
                            response.path("choices").get(0).path("message").path("content").asText()
                    );
                });
    }

    private Optional<Map<String, String>> extraerArgumentosFuncion(JsonNode response) {
        JsonNode toolCalls = response.path("choices").get(0).path("message").path("tool_calls");

        if (toolCalls.isArray() && toolCalls.size() > 0) {
            JsonNode functionCall = toolCalls.get(0).path("function");
            String name = functionCall.path("name").asText();

            if ("obtenerHistorialViajes".equals(name)) {
                try {
                    JsonNode args = new ObjectMapper().readTree(functionCall.path("arguments").asText());
                    return Optional.of(Map.of(
                            "idUsuario", String.valueOf(args.path("idUsuario").asLong()),
                            "fechaDesde", args.path("fechaDesde").asText(),
                            "fechaHasta", args.path("fechaHasta").asText()
                    ));
                } catch (JsonProcessingException e) {
                    throw new RuntimeException("Error al parsear argumentos de función", e);
                }
            }
        }
        return Optional.empty();
    }

    private Mono<String> llamarEndpointHistorial(Map<String, String> params) {
        return WebClient.create()
                .get()
                .uri("http://localhost:8003/viajes/historial?idUsuario={idUsuario}&fechaDesde={fechaDesde}&fechaHasta={fechaHasta}",
                        params.get("idUsuario"), params.get("fechaDesde"), params.get("fechaHasta"))
                .retrieve()
                .bodyToMono(String.class);
    }


}
