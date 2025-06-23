package org.example.msvcfacturacion.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.example.msvcfacturacion.models.TiemposViaje;

import java.util.Date;

@Data
public class FacturaRequestDTO {

    @NotNull(message = "El idUsuario no puede ser nulo")
    private Long idUsuario;

    @NotNull(message = "El idViaje no puede ser nulo")
    private Long idViaje;

    @NotNull(message = "La fecha no puede ser nula (formato yyyy-MM-dd)")
    private String fecha; // formato esperado: "2025-04-10"

    @NotNull(message = "La hora no puede ser nula (formato HH:mm:ss)")
    private String hora;// formato esperado: "22:33:13"

    @NotNull(message = "Los tiempos no pueden ser nulos")
    private TiemposViaje tiempos;
}
