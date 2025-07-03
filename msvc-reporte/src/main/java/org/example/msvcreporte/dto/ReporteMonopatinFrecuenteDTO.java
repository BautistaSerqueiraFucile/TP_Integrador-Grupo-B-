package org.example.msvcreporte.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO que representa un monopatín frecuente con su cantidad de viajes")
public class ReporteMonopatinFrecuenteDTO {

    @Schema(description = "Identificador único del monopatín", example = "MONO123")
    public final String idMonopatin;

    @Schema(description = "Cantidad de viajes realizados por el monopatín", example = "87")
    public final Integer cantidadViajes;

    public ReporteMonopatinFrecuenteDTO(String idMonopatin, Integer cantidadViajes) {
        this.idMonopatin = idMonopatin;
        this.cantidadViajes = cantidadViajes;
    }
}
