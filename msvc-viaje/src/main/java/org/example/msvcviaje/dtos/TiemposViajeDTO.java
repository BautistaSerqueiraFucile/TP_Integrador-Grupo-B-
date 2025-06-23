package org.example.msvcviaje.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalTime;

@AllArgsConstructor
@Data
public class TiemposViajeDTO {
    private double tiempoTotal;
    private double tiempoPausa;
}
