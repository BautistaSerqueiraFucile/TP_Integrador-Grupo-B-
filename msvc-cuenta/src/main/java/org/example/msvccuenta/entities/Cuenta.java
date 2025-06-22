package org.example.msvccuenta.entities;

import jakarta.persistence.*;
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
    @Column(name = "fechaAlta")
    private LocalDate fechaAlta;
    @Column(name = "tipoCuenta", nullable = false)
    @Enumerated(EnumType.STRING)
    private TipoCuenta tipoCuenta;
    @Column(name = "saldo")
    private Double saldo;
    @Column(name = "mercadoPagoId")
    private String mercadoPagoId;
    @Column(name = "estadoCuenta")
    @Enumerated(EnumType.STRING)
    private EstadoCuenta estadoCuenta;
    @Column(name = "kmRecorridosMesPremium")
    private Double kmRecorridosMesPremium;
    @Column(name = "usuarios")
    @ElementCollection
    private Set<Long> usuariosId = new HashSet<>();

}


