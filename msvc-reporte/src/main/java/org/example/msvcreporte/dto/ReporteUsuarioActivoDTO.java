package org.example.msvcreporte.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReporteUsuarioActivoDTO {
    private Long idUsuario;
    private String tipoCuenta;
    private Integer cantidadViajes;

    public ReporteUsuarioActivoDTO() {}
}


