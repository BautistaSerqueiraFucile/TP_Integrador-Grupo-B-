package org.example.msvcreporte.dto;

public class ReporteUsoPorCuentaDTO {
    private Long idUsuario;
    private Double kmRecorridos;
    private Long tiempoTotal;
    private Integer cantidadViajes;

    public ReporteUsoPorCuentaDTO() {}

    public ReporteUsoPorCuentaDTO(Long idUsuario, Double kmRecorridos, Long tiempoTotal, Integer cantidadViajes) {
        this.idUsuario = idUsuario;
        this.kmRecorridos = kmRecorridos;
        this.tiempoTotal = tiempoTotal;
        this.cantidadViajes = cantidadViajes;
    }
}
