package org.example.msvcviaje.dtos;

import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@AllArgsConstructor
public class FacturaRequestDTO {

    private Long idUsuario;

    private Long idViaje;

    private LocalDate fecha; // formato esperado: "2025-04-10"

    private LocalTime hora;// formato esperado: "22:33:13"

}
