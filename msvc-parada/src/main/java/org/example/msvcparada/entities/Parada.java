package org.example.msvcparada.entities;

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
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "posx")
    private double posX;

    @Column(name = "posy")
    private double posY;

    @Column(name = "activa")
    private boolean activa;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Parada parada = (Parada) o;
        return Double.compare(posX, parada.posX) == 0 && Double.compare(posY, parada.posY) == 0  && Objects.equals(nombre, parada.nombre);
    }

}
