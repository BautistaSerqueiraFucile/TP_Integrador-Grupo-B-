package org.example.msvcfacturacion.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    public Tarifa(double basico, double premium, double pausa){
        this.tarifaBasica = basico;
        this.tarifaPremium = premium;
        this.tarifaPausa = pausa;
    }
}
