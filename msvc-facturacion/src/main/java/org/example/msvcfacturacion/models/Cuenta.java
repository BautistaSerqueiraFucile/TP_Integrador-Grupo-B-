package org.example.msvcfacturacion.models;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
public class Cuenta {
    private Long id;
    private LocalDate fechaAlta;
    private String tipoCuenta;
    private BigDecimal saldo;
    private String mercadoPagoId;
    private String estadoCuenta;
    private BigDecimal kmRecorridosMesPremium;
    private Set<Long> usuariosId = new HashSet<>();
}
