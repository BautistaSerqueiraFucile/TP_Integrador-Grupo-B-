package org.example.msvcmonopatin.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "DTO con estadísticas de uso del monopatín")
public class MonopatinEstadisticasDTO {

    @Schema(
            description = "ID único del monopatín",
            example = "mnp-001"
    )
    private String idMonopatin;

    @Schema(
            description = "Distancia total recorrida por el monopatín (en kilómetros)",
            example = "254.75"
    )
    private double kilometros;

    @Schema(
            description = "Tiempo total de uso del monopatín (en horas)",
            example = "35.2"
    )
    private double tiempoTotal;

    @Schema(
            description = "Tiempo total en pausa (en horas)",
            example = "4.75"
    )
    private double tiempoPausa;
}
