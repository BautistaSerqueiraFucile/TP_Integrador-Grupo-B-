package org.example.msvcadmin.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Monopatin {

    private String id;
    private double kilometrosActuales;
    private String estado;
    private Ubicacion ubicacion;
    private String paradaActual;
    private double tiempoDeUso;
    private double tiempoDePausa;
}
