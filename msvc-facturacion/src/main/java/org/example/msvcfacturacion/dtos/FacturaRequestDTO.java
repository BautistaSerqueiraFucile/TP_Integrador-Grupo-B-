package org.example.msvcfacturacion.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Date;

@Data
public class FacturaRequestDTO {

    @NotNull(message = "El idUsuario no puede ser nulo")
    private Long idUsuario;

    @NotNull(message = "El idViaje no puede ser nulo")
    private Long idViaje;

    @Min(value = 1)
    @NotNull(message = "La tarifa básica debe ser mayor o igual a 1")
    private double tarifaBasica;

    @NotNull
    private double tarifaPremium;

    @NotNull
    private double precio;

    @NotNull(message = "La fecha no puede ser nula (formato yyyy-MM-dd)")
    private String fecha; // formato esperado: "2025-04-10"

    @NotNull(message = "La hora no puede ser nula (formato HH:mm:ss)")
    private String hora;  // formato esperado: "22:33:13"
}
