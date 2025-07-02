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

    public Integer getAnio() {
        return anio;
    }

    public Integer getMesDesde() {
        return mesDesde;
    }

    public Integer getMesHasta() {
        return mesHasta;
    }

    public Double getTotalFacturado() {
        return totalFacturado;
    }
}
