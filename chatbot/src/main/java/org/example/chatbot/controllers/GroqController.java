package org.example.chatbot.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.example.chatbot.dtos.ChatRequestDTO;
import org.example.chatbot.services.GroqService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
@RequestMapping("/chat")
public class GroqController {

    private final GroqService groqService;

    public GroqController(GroqService groqService) {
        this.groqService = groqService;
    }

    @PostMapping("consulta")
    public Mono<ResponseEntity<String>> procesarConsulta(@RequestBody ChatRequestDTO request) throws JsonProcessingException {

        return groqService.procesarConsulta(request.getMensaje())
                .map(respuesta -> ResponseEntity.ok(respuesta))
                .onErrorResume(e ->
                        Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body("Error procesando la solicitud: " + e.getMessage()))
                );
    }
}

