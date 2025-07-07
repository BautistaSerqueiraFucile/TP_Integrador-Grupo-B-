package org.example.msvccuenta.entities;
import io.swagger.v3.oas.annotations.media.Schema;
/**
 * Define el tipo de plan de una cuenta, que puede ser BASICA o PREMIUM.
 */
@Schema(description = "Define el tipo de plan de una cuenta, que puede ser BASICA o PREMIUM.")
public enum TipoCuenta {
    BASICA, PREMIUM
}