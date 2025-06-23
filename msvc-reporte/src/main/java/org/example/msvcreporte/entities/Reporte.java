package org.example.msvcreporte.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "reportes")
public class Reporte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Fecha y hora de generación del reporte
    @Column(nullable = false)
    private LocalDateTime fechaGeneracion;

    // Tipo del reporte (uso_monopatin, facturacion_periodo, usuarios_activos, etc.)
    @Column(nullable = false)
    private String tipo;

    // Parámetros usados para generar el reporte (JSON serializado)
    @Column(columnDefinition = "TEXT")
    private String parametros;

    // Resultado serializado del reporte (puede ser JSON o string según tu criterio)
    @Column(columnDefinition = "LONGTEXT")
    private String resultado;

    // Usuario (admin) que solicitó el reporte, si aplica
    private String solicitadoPor;

    // Estado del reporte (por si los hacés asíncronos en algún momento: PENDIENTE, OK, ERROR)
    @Column(nullable = false)
    private String estado;

    // Mensaje de error si falló
    private String mensajeError;

    // Constructor vacío requerido por JPA
    public Reporte() {}

    // Constructor útil
    public Reporte(LocalDateTime fechaGeneracion, String tipo, String parametros, String resultado, String solicitadoPor, String estado, String mensajeError) {
        this.fechaGeneracion = fechaGeneracion;
        this.tipo = tipo;
        this.parametros = parametros;
        this.resultado = resultado;
        this.solicitadoPor = solicitadoPor;
        this.estado = estado;
        this.mensajeError = mensajeError;
    }


}
