package org.example.chatbot.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RespuestaApi<T> {
    private boolean ok;
    private String mensaje;
    private T datos;
}
