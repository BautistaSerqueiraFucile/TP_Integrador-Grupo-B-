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
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Representa una cuenta de usuario en el sistema de monopatines.")
@Getter
@Setter
@Entity
@Table(name = "cuenta")
public class Cuenta {
    @Schema(description = "Identificador único de la cuenta, generado automáticamente.", example = "2001")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(description = "Fecha en que la cuenta fue creada.", example = "2023-01-01")
    @NotNull
    @Column(name = "fechaAlta", nullable = false)
    private LocalDate fechaAlta;

    @Schema(description = "Tipo de plan de la cuenta (BASICA o PREMIUM).")
    @NotNull
    @Column(name = "tipoCuenta", nullable = false)
    @Enumerated(EnumType.STRING)
    private TipoCuenta tipoCuenta;

    @Schema(description = "Saldo disponible en la cuenta para realizar viajes.", example = "15000.0")
    @NotNull
    @Column(name = "saldo", nullable = false)
    private Double saldo;

    @Schema(description = "Identificador de la cuenta de MercadoPago asociada para recargas.", example = "mp_2001")
    @NotEmpty
    @Column(name = "mercadoPagoId", nullable = false)
    private String mercadoPagoId;

    @Schema(description = "Estado actual de la cuenta (ACTIVA o ANULADA).")
    @NotNull
    @Column(name = "estadoCuenta", nullable = false)
    @Enumerated(EnumType.STRING)
    private EstadoCuenta estadoCuenta;

    @Schema(description = "Total de kilómetros recorridos en el mes. Aplica solo a cuentas PREMIUM.", example = "5.0", nullable = true)
    @Column(name = "kmRecorridosMesPremium")
    private Double kmRecorridosMesPremium;

    @Schema(description = "Conjunto de IDs de los usuarios asociados a esta cuenta.")
    @NotNull
    @Column(name = "usuarios")
    @ElementCollection
    private Set<Long> usuariosId = new HashSet<>();

}


