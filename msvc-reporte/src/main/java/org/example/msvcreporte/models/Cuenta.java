package org.example.msvcreporte.models;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
public class Cuenta {
    private Long id;
    private LocalDate fechaAlta;
    private String tipoCuenta;
    private double saldo;
    private String mercadoPagoId;
    private String estadoCuenta;
    private double kmRecorridosMesPremium;
    private Set<Long> usuariosId = new HashSet<>();
}
