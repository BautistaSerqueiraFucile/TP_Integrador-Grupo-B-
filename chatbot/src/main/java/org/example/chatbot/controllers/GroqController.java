package org.example.chatbot.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.example.chatbot.dtos.ChatRequestDTO;
import org.example.chatbot.services.GroqService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/chat")
public class GroqController {

    private final GroqService groqService;

    public GroqController(GroqService groqService) {
        this.groqService = groqService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("consulta")
    public Mono<String> procesarConsulta(
            @RequestBody ChatRequestDTO request,
            @RequestHeader("Authorization") String authHeader
    ) throws JsonProcessingException {
        return groqService.procesarConsulta(request.getMensaje(), authHeader)
                .doOnSubscribe(s -> System.out.println("🔍 Controller: iniciando consulta..."))
                .doOnNext(r -> System.out.println("✅ Controller: resultado recibido => " + r))
                .doOnError(e -> System.out.println("❌ Controller: error => " + e.getMessage()));
    }
}

