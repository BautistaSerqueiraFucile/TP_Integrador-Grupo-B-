package org.example.msvcfacturacion.dtos;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class TarifaRequestDTO {
    private double tarifaBasica;

    private double tarifaPremium;

    private double tarifaPausa;

    private LocalDate fechaAumento;

    private double porcentajeAumento;

}
