package org.example.chatbot.client;

import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Component
public class OllamaClient {  // OllamaClient encapsula cómo hablar con Ollama vía HTTP.

    private static final String OLLAMA_URL = "http://localhost:11434/api/generate";

    public String preguntar(String pregunta) {
        RestTemplate restTemplate = new RestTemplate(); //👉 Instancia RestTemplate para enviar solicitudes HTTP.

        // Construimos el body para Ollama
        Map<String, Object> requestBody = Map.of(
                "model", "llama3",  // model: modelo de Ollama (aquí "llama3").
                "prompt", pregunta,       // pregunta a procesar.
                "stream", false // para que la respuesta llegue completa
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON); // 👉 Configura encabezados HTTP, indicando que el cuerpo es JSON.

        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers); // 👉 Crea la entidad HTTP con cuerpo + headers.

        try { // 👉 Hace una petición POST a Ollama.
            // exchange envía la request y espera un Map JSON como respuesta.
            ResponseEntity<Map> response = restTemplate.exchange(
                    OLLAMA_URL,
                    HttpMethod.POST,
                    requestEntity,
                    Map.class
            );

            // Extraemos el campo "response" del JSON devuelto por Ollama. Si existe, lo devuelve limpio; si no, "Sin respuesta".
            Object respuesta = response.getBody().get("response");
            return respuesta != null ? respuesta.toString().trim() : "Sin respuesta";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error al consultar Ollama: " + e.getMessage(); //👉 Si falla la llamada, imprime stack trace y devuelve mensaje de error.
        }
    }
}
