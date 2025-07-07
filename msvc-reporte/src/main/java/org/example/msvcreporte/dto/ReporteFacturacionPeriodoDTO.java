package org.example.msvcreporte.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

@Schema(description = "DTO que representa un reporte de facturación en un período determinado")
public class ReporteFacturacionPeriodoDTO {

    @Schema(description = "ID del usuario asociado al reporte", example = "12345")
    public final Long idUsuario;

    @Schema(description = "Fecha de inicio del período", example = "2024-01-01")
    public final LocalDate desde;

    @Schema(description = "Fecha de fin del período", example = "2024-01-31")
    public final LocalDate hasta;

    @Schema(description = "Total facturado en el período", example = "15000.75")
    public final Double totalFacturado;

    public ReporteFacturacionPeriodoDTO(Long idUsuario, LocalDate desde, LocalDate hasta, Double totalFacturado) {
        this.idUsuario = idUsuario;
        this.desde = desde;
        this.hasta = hasta;
        this.totalFacturado = totalFacturado;
    }
}
