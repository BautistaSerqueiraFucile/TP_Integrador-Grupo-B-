package org.example.msvcadmin.models;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class Factura {

    private Long idFactura;

    private Long idUsuario;

    private Long idViaje;

    private String tipoCuenta = null;

    private double costoTarifa = 0;

    private double tarifaPausa = 0;

    private double precioViaje = 0;

    private LocalDate fecha; // o popdria ser Solo la fecha con LocalDate (yyyy-MM-dd)
}
