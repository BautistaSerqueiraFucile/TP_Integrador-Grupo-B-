package org.example.msvcreporte.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO que representa el uso total de monopatines por cuenta")
public class ReporteUsoPorCuentaDTO {

    @Schema(description = "ID del usuario", example = "12345")
    public final Long idUsuario;

    @Schema(description = "Kilómetros recorridos por el usuario", example = "1500.75")
    public final Double kmRecorridos;

    @Schema(description = "Tiempo total de uso en minutos", example = "3600")
    public final Long tiempoTotal;

    @Schema(description = "Cantidad total de viajes realizados", example = "120")
    public final Integer cantidadViajes;

    public ReporteUsoPorCuentaDTO(Long idUsuario, Double kmRecorridos, Long tiempoTotal, Integer cantidadViajes) {
        this.idUsuario = idUsuario;
        this.kmRecorridos = kmRecorridos;
        this.tiempoTotal = tiempoTotal;
        this.cantidadViajes = cantidadViajes;
    }
}
