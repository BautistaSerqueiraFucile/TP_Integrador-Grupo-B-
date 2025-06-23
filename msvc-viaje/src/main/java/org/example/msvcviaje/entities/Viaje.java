package org.example.msvcviaje.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.msvcviaje.dtos.ViajeRequestDTO;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "viaje")
public class Viaje {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_viaje")
    private Long idViaje;

    @Column(name = "id_usuario", nullable = false)
    private Long idUsuario;

    @Column(name = "id_monopatin", nullable = false)
    private String idMonopatin = null;

    @Column(name = "id_parada_inicio", nullable = false)
    private Long idParadaInicio;

    @Column(name = "id_parada_fin", nullable = false)
    private Long idParadaFin;

    @Column(name = "fecha", nullable = false)
    private LocalDate fecha; // Solo la fecha (yyyy-MM-dd)

    @Column(name = "hora_inicio", nullable = false)
    private LocalTime horaInicio; // Solo hora (HH:mm:ss)

    @Column(name = "hora_fin")
    private LocalTime horaFin = null; // Solo hora (HH:mm:ss)

    @Column(name = "tiempo_pausa")
    private double tiempoPausa = 0; //minutos

    @Column(name = "estado", nullable = false)
    @Enumerated(EnumType.STRING)
    private EstadoViaje estado = EstadoViaje.activo; //activo, pausado ,finalizado

    @Column(name = "kilometros", nullable = false)
    private double kilometros = 0;

    public Viaje(ViajeRequestDTO dto) {
        this.idUsuario = dto.getIdUsuario();
        this.idParadaInicio = dto.getIdParadaInicio();
        this.idParadaFin = dto.getIdParadaFin();
        this.fecha = LocalDate.parse(dto.getFecha());
        this.horaInicio = LocalTime.parse(dto.getHoraInicio());
    }
}
