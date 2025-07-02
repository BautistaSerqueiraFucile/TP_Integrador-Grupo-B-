package org.example.msvcreporte.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class MonopatinViajeDTO {

    private String idMonopatin;
    private Integer anio;
    private Long cantidadDeViajes;

    public MonopatinViajeDTO(String idMonopatin, Integer anio, Long cantidadDeViajes) {
        this.idMonopatin = idMonopatin;
        this.anio = anio;
        this.cantidadDeViajes = cantidadDeViajes;
    }

}
