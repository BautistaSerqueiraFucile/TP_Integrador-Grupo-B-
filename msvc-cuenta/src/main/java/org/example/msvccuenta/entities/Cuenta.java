package org.example.msvccuenta.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
@Getter
@Setter
@Entity
@Table(name = "cuenta")
public class Cuenta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "fechaAlta", nullable = false)
    private LocalDate fechaAlta;

    @NotNull
    @Column(name = "tipoCuenta", nullable = false)
    @Enumerated(EnumType.STRING)
    private TipoCuenta tipoCuenta;

    @NotNull
    @Column(name = "saldo", nullable = false)
    private Double saldo;

    @NotEmpty
    @Column(name = "mercadoPagoId", nullable = false)
    private String mercadoPagoId;

    @NotNull
    @Column(name = "estadoCuenta", nullable = false)
    @Enumerated(EnumType.STRING)
    private EstadoCuenta estadoCuenta;

    @Column(name = "kmRecorridosMesPremium")
    private Double kmRecorridosMesPremium;

    @NotNull
    @Column(name = "usuarios")
    @ElementCollection
    private Set<Long> usuariosId = new HashSet<>();

}


