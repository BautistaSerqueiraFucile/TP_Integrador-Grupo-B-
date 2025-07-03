package org.example.msvcreporte.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO que representa el uso de un monopatín con estadísticas de viaje")
public class ReporteUsoMonopatinDTO {

    @Schema(description = "Identificador del monopatín", example = "MONO456")
    public final String idMonopatin;

    @Schema(description = "Cantidad total de kilómetros recorridos", example = "123.45")
    public final double kilometros;

    @Schema(description = "Tiempo total de uso en minutos", example = "85.0")
    public final double tiempoTotal;

    @Schema(description = "Tiempo total en pausa en minutos", example = "12.0")
    public final double tiempoPausa;

    public ReporteUsoMonopatinDTO(String idMonopatin, double kilometros, double tiempoTotal, double tiempoPausa) {
        this.idMonopatin = idMonopatin;
        this.kilometros = kilometros;
        this.tiempoTotal = tiempoTotal;
        this.tiempoPausa = tiempoPausa;
    }
}
