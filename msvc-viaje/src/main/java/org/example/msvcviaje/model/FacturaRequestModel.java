package org.example.msvcviaje.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class FacturaRequestModel {

    private Long idUsuario;

    private Long idViaje;

    private LocalDate fecha; // formato esperado: "2025-04-10"

    private double tiempoTotal;

    private double tiempoPausa;

}
