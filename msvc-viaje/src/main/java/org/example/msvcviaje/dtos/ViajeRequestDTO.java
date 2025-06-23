package org.example.msvcviaje.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ViajeRequestDTO {

    @NotNull
    private Long idUsuario;

    @NotNull
    private Long idParadaInicio;

    @NotNull
    private Long idParadaFin;

    @NotNull
    private String fecha; // formato esperado: "yyyy-MM-dd"

    @NotNull
    private String horaInicio; // formato esperado: "HH:mm:ss"

}
