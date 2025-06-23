package org.example.msvcviaje.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Monopatin {

    private String id;
    private double kilometrosActuales;
    private String estado;
    private Ubicacion ubicacion;
    private Long parada;
    private double tiempoDeUso;
    private double tiempoDePausa;
}
