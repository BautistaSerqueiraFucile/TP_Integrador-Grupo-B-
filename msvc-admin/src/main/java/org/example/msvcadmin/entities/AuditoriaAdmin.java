package org.example.msvcadmin.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "auditoria_admin")
public class AuditoriaAdmin {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private UUID adminId;

    @Column(nullable = false)
    private LocalDateTime fecha;

    @Column(nullable = false)
    private String tipoAccion;

    @Column(nullable = false)
    private String entidadAfectadaId;

    @Column(length = 1000)
    private String detalle;

    // --- Getters ---

    public UUID getId() {
        return id;
    }

    public UUID getAdminId() {
        return adminId;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public String getTipoAccion() {
        return tipoAccion;
    }

    public String getEntidadAfectadaId() {
        return entidadAfectadaId;
    }

    public String getDetalle() {
        return detalle;
    }

    // --- Setters ---

    public void setId(UUID id) {
        this.id = id;
    }

    public void setAdminId(UUID adminId) {
        this.adminId = adminId;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public void setTipoAccion(String tipoAccion) {
        this.tipoAccion = tipoAccion;
    }

    public void setEntidadAfectadaId(String entidadAfectadaId) {
        this.entidadAfectadaId = entidadAfectadaId;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }
}
