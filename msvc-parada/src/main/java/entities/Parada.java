package entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.autoconfigure.web.WebProperties;

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


}
