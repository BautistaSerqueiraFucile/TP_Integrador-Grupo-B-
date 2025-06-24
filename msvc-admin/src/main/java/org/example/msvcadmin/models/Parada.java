package org.example.msvcadmin.models;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Parada {

    private Long id;
    private String nombre;
    private double posX;
    private double posY;
    private boolean activa;
}
