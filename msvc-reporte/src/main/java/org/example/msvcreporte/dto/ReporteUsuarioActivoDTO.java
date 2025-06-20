package org.example.msvcreporte.dto;

public class ReporteUsuarioActivoDTO {
    private Long idUsuario;
    private String nombre;
    private Integer cantidadViajes;
    private Double kmTotales;

    public ReporteUsuarioActivoDTO() {}

    public ReporteUsuarioActivoDTO(Long idUsuario, String nombre, Integer cantidadViajes, Double kmTotales) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.cantidadViajes = cantidadViajes;
        this.kmTotales = kmTotales;
    }

    public Integer getCantidadViajes() {
        return cantidadViajes;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public Double getKmTotales() {
        return kmTotales;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setCantidadViajes(Integer cantidadViajes) {
        this.cantidadViajes = cantidadViajes;
    }

    public void setKmTotales(Double kmTotales) {
        this.kmTotales = kmTotales;
    }
}
