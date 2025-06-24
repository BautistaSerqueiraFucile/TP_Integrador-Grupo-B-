package org.example.msvcreporte.dto;

public class ReporteMonopatinFrecuenteDTO {
    private String idMonopatin;
    private Integer cantidadViajes;

    public ReporteMonopatinFrecuenteDTO() {}
//esto
    public ReporteMonopatinFrecuenteDTO(String idMonopatin, Integer cantidadViajes) {
        this.idMonopatin = idMonopatin;
        this.cantidadViajes = cantidadViajes;
    }
}
