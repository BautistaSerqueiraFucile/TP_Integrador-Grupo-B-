package org.example.msvcreporte.dto;

public class ReporteFacturacionPeriodoDTO {
    private Integer anio;
    private Integer mesDesde;
    private Integer mesHasta;
    private Double totalFacturado;

    public ReporteFacturacionPeriodoDTO() {}

    public ReporteFacturacionPeriodoDTO(Integer anio, Integer mesDesde, Integer mesHasta, Double totalFacturado) {
        this.anio = anio;
        this.mesDesde = mesDesde;
        this.mesHasta = mesHasta;
        this.totalFacturado = totalFacturado;
    }
}
