package org.example.msvccuenta.entities.dto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;
/**
 * DTO que representa los detalles de un viaje realizado por un usuario.
 * Se utiliza para obtener el historial de viajes.
 */
@Schema(description = "DTO que representa los detalles de un viaje realizado.")
@Getter
public class ViajeDto {
    @Schema(description = "ID único del viaje.", example = "501")
    private Long idViaje;
    @Schema(description = "ID del usuario que realizó el viaje.", example = "1")
    private Long idUsuario;
    @Schema(description = "ID del monopatín utilizado en el viaje.", example = "MONO-007")
    private String idMonopatin;
    @Schema(description = "ID de la parada donde inició el viaje.", example = "101")
    private Long idParadaInicio;
    @Schema(description = "ID de la parada donde finalizó el viaje.", example = "105")
    private Long idParadaFin;
    @Schema(description = "Fecha en que se realizó el viaje.", type = "string", format = "date", example = "2024-05-20")
    private LocalDate fecha;

    @Schema(description = "Hora de inicio del viaje.", type = "string", format = "time", example = "14:30:00")
    private LocalTime horaInicio;

    @Schema(description = "Hora de finalización del viaje.", type = "string", format = "time", example = "14:45:00")
    private LocalTime horaFin;
    @Schema(description = "Tiempo total en pausa durante el viaje (en minutos).", example = "2.5")
    private double tiempoPausa;
    @Schema(description = "Estado final del viaje (e.g., FINALIZADO).", example = "FINALIZADO")
    private String estado;
    @Schema(description = "Distancia total del viaje en kilómetros.", example = "1.8")
    private double kilometros;
}
