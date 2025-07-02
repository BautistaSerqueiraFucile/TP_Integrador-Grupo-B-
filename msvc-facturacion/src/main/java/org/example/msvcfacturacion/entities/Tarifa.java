package org.example.msvcfacturacion.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.msvcfacturacion.dtos.TarifaRequestDTO;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
public class Tarifa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tarifa")
    private long idTarifa;
    @Column(name = "tarifa_basica")
    private double tarifaBasica;
    @Column(name = "tarifa_premium")
    private double tarifaPremium;
    @Column(name = "tarifa_pausa")
    private double tarifaPausa;
    @Column(name = "fecha_aumento")
    private LocalDate fechaAumento;
    @Column(name = "porcentaje_aumento")
    private double porcentajeAumento;

    public Tarifa(TarifaRequestDTO tarifaRequestDTO) {
        this.tarifaBasica = tarifaRequestDTO.getTarifaBasica();
        this.tarifaPremium = tarifaRequestDTO.getTarifaPremium();
        this.tarifaPausa = tarifaRequestDTO.getTarifaPausa();
        this.fechaAumento = tarifaRequestDTO.getFechaAumento();
        this.porcentajeAumento = tarifaRequestDTO.getPorcentajeAumento();
    }
}
