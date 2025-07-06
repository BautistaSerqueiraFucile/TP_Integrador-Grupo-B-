package org.example.msvccuenta.entities;
import io.swagger.v3.oas.annotations.media.Schema;
/**
 * Define el estado de una cuenta, que puede ser ACTIVA o ANULADA.
 */
@Schema(description = "Define el estado de una cuenta, que puede ser ACTIVA o ANULADA.")
public enum EstadoCuenta {
    ACTIVA, ANULADA
}