package org.example.msvcadmin.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Tarifa {
    private long id;
    private double tarifaBasica;
    private double tarifaPremium;
    private double tarifaPausa;
}
