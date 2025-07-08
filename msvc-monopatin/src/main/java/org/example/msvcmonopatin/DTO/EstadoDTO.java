package org.example.msvcmonopatin.DTO;

import io.swagger.v3.oas.annotations.media.Schema;


@Schema(description = "DTO que representa el estado actual de un monopatín")
public class EstadoDTO {

    @Schema(
            description = "Estado del monopatín (por ejemplo: DISPONIBLE, EN_USO, MANTENIMIENTO)",
            example = "DISPONIBLE"
    )
    private String estado;

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
