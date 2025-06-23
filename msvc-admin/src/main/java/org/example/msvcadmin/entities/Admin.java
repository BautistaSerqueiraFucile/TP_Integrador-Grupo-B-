package org.example.msvcadmin.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "admins")
public class Admin {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false, unique = true)
    private String userId;

    @Column(nullable = false)
    private LocalDateTime fechaAsignacion;

    @Column(nullable = false)
    private boolean activo = true;

    // --- Getters ---

    public UUID getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public LocalDateTime getFechaAsignacion() {
        return fechaAsignacion;
    }

    public boolean isActivo() {
        return activo;
    }

    // --- Setters ---

    public void setId(UUID id) {
        this.id = id;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setFechaAsignacion(LocalDateTime fechaAsignacion) {
        this.fechaAsignacion = fechaAsignacion;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

}
