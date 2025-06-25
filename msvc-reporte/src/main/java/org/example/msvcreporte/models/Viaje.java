package org.example.msvcreporte.models;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.time.LocalDate;
import java.time.LocalTime;

public class Viaje {
    private Long idViaje;

    private Long idUsuario;

    private String idMonopatin = null;

    private Long idParadaInicio;

    private Long idParadaFin;

    private LocalDate fecha; // Solo la fecha (yyyy-MM-dd)

    private LocalTime horaInicio; // Solo hora (HH:mm:ss)

    private LocalTime horaFin = null; // Solo hora (HH:mm:ss)

    private double tiempoPausa = 0; //minutos

    private EstadoViaje estado = EstadoViaje.activo; //activo, pausado ,finalizado

    private double kilometros = 0;
}
