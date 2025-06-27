package org.example.msvcadmin.models;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class Tarifa {
    private double tarifaBasica;

    private double tarifaPremium;

    private double tarifaPausa;

    private LocalDate fechaAumento;

    private double porcentajeAumento;
}
