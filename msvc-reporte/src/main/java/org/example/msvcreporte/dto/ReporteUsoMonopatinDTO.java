package org.example.msvcreporte.dto;

public class ReporteUsoMonopatinDTO {
    private String idMonopatin;
    private Double kmRecorridos;
    private Long tiempoTotal;
    private Long tiempoConPausas;
    private Long tiempoSinPausas;
    private Boolean requiereMantenimiento;
    private Integer cantidadViajes;

    public ReporteUsoMonopatinDTO() {}

    public ReporteUsoMonopatinDTO(String idMonopatin, Double kmRecorridos, Long tiempoTotal,
                                  Long tiempoConPausas, Long tiempoSinPausas,
                                  Boolean requiereMantenimiento, Integer cantidadViajes) {
        this.idMonopatin = idMonopatin;
        this.kmRecorridos = kmRecorridos;
        this.tiempoTotal = tiempoTotal;
        this.tiempoConPausas = tiempoConPausas;
        this.tiempoSinPausas = tiempoSinPausas;
        this.requiereMantenimiento = requiereMantenimiento;
        this.cantidadViajes = cantidadViajes;
    }
}
