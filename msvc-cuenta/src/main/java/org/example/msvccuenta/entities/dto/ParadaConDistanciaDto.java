package org.example.msvccuenta.entities.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO que extiende la información de una Parada para incluir la distancia
 * calculada desde la ubicación de un usuario.
 */
@Getter
@Setter
@Schema(description = "Representa una parada con la distancia calculada a un usuario.")
public class ParadaConDistanciaDto extends ParadaDto {

    @Schema(description = "Distancia Euclidiana calculada desde el usuario hasta la parada.", example = "36.05")
    private Double distancia;

    /**
     * Constructor para crear el DTO a partir de una parada existente y la distancia calculada.
     * @param paradaDto El DTO original de la parada.
     * @param distancia La distancia calculada.
     */
    public ParadaConDistanciaDto(ParadaDto paradaDto, Double distancia) {
        // Copiamos los atributos de la parada original
        this.setId(paradaDto.getId());
        this.setNombre(paradaDto.getNombre());
        this.setPosX(paradaDto.getPosX());
        this.setPosY(paradaDto.getPosY());
        this.setActiva(paradaDto.isActiva());
        // Añadimos el nuevo atributo
        this.distancia = distancia;
    }
}