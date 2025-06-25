package org.example.msvcmonopatin.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MonopatinEstadisticasDTO {
    private String idMonopatin;
    private double kilometros;
    private double tiempoTotal;
    private double tiempoPausa;

}
