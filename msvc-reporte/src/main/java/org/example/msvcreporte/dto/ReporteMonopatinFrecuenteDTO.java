package org.example.msvcreporte.dto;

public class ReporteMonopatinFrecuenteDTO {
    private Long idMonopatin;
    private Integer cantidadViajes;

    public ReporteMonopatinFrecuenteDTO() {}

    public ReporteMonopatinFrecuenteDTO(Long idMonopatin, Integer cantidadViajes) {
        this.idMonopatin = idMonopatin;
        this.cantidadViajes = cantidadViajes;
    }
}
