package org.example.msvccuenta.entities.dto;

import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
public class ViajeDto {
    private Long idViaje;
    private Long idUsuario;
    private String idMonopatin;
    private Long idParadaInicio;
    private Long idParadaFin;
    private LocalDate fecha;
    private LocalTime horaInicio;
    private LocalTime horaFin;
    private double tiempoPausa;
    private String estado;
    private double kilometros;
}
