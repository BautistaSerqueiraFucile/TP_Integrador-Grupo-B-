package org.example.msvcreporte.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReporteUsoMonopatinDTO {
    private String idMonopatin;
    private double kilometros;
    private double tiempoTotal;
    private double tiempoPausa;
}
