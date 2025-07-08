package org.example.msvcparada.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.autoconfigure.web.WebProperties;

import java.util.Objects;

@Entity
@Table(name = "parada")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Parada {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador único de la parada", example = "1")
    private Long id;

    @Column(name = "nombre")
    @Schema(description = "Nombre de la parada", example = "Parada Central")
    private String nombre;

    @Column(name = "posx")
    @Schema(description = "Posición X en el mapa (coordenada horizontal)", example = "45.67")
    private double posX;

    @Column(name = "posy")
    @Schema(description = "Posición Y en el mapa (coordenada vertical)", example = "87.12")
    private double posY;

    @Column(name = "activa")
    @Schema(description = "Indica si la parada está activa", example = "true")
    private boolean activa;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Parada parada = (Parada) o;
        return Double.compare(posX, parada.posX) == 0 &&
                Double.compare(posY, parada.posY) == 0 &&
                Objects.equals(nombre, parada.nombre);
    }
}
