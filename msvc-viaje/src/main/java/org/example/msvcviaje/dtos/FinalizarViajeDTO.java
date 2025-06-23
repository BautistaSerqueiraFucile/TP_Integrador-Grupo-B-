package org.example.msvcviaje.dtos;

import lombok.Data;

import java.time.LocalTime;

@Data
public class FinalizarViajeDTO {
    private LocalTime horaFin;
    private int tiempoPausa;
}
