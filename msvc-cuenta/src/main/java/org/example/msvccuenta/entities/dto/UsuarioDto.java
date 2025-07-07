package org.example.msvccuenta.entities.dto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Getter;
/**
 * DTO con la información de ubicación de un usuario.
 * Se utiliza para calcular distancias.
 */
@Schema(description = "DTO con la información de ubicación de un usuario.")
@Getter
@Data
public class UsuarioDto {
    @Schema(description = "ID único del usuario.", example = "1")
    private Long id;
    @Schema(description = "Coordenada de latitud actual del usuario.", example = "-34.6045")
    private Double latitud;
    @Schema(description = "Coordenada de longitud actual del usuario.", example = "-58.3820")
    private Double longitud;
}
