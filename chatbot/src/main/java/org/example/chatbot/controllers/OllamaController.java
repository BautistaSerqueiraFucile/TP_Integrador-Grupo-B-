package org.example.chatbot.controllers;

import org.example.chatbot.services.OllamaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bot")
public class OllamaController {

    @Autowired
    private OllamaService ollamaService;

    @PostMapping(value = "/prompt", produces = "application/json") // 👉 Define endpoint POST /api/ia/prompt que recibe un prompt como cuerpo JSON.
    public ResponseEntity<?> procesarPrompt(@RequestBody String prompt) {
        try {
            return ollamaService.procesarPrompt(prompt);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al procesar el prompt: " + e.getMessage());
        }
    }
}
