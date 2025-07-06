package org.example.msvccuenta.entities.dto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

/**
 * DTO (Data Transfer Object) que representa los datos de una parada de monopatín.
 * Se utiliza para la comunicación entre microservicios.
 */
@Schema(description = "DTO que representa los datos de una parada de monopatín.")
@Getter
public class ParadaDto {
    @Schema(description = "ID único de la parada.", example = "101")
    private Long id;
    @Schema(description = "Nombre o identificador de la parada.", example = "Plaza Central")
    private String nombre;
    @Schema(description = "Coordenada X (longitud) de la ubicación de la parada.", example = "-58.3816")
    private double posX;
    @Schema(description = "Coordenada Y (latitud) de la ubicación de la parada.", example = "-34.6037")
    private double posY;
    @Schema(description = "Indica si la parada está actualmente en servicio.", example = "true")
    private boolean activa;
}
