package org.example.msvcreporte.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
@Data
@AllArgsConstructor
public class ReporteFacturacionPeriodoDTO {
    private Long idUsuario;
    private LocalDate desde;
    private LocalDate hasta;
    private Double totalFacturado;

    public ReporteFacturacionPeriodoDTO(LocalDate desde, LocalDate hasta, Double totalFacturado) {
        this.desde = desde;
        this.hasta = hasta;
        this.totalFacturado = totalFacturado;
    }
}
