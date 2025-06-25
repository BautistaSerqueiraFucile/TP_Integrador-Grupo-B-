package org.example.msvcreporte.controllers;

import org.example.msvcreporte.dto.ReporteUsuarioActivoDTO;
import org.example.msvcreporte.services.ReporteUsuariosActivosService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/reporte")
public class ReporteUsuariosActivosController {

    private final ReporteUsuariosActivosService servicio;

    public ReporteUsuariosActivosController(ReporteUsuariosActivosService servicio) {
        this.servicio = servicio;
    }

    @GetMapping("/usuarios-top")
    public ResponseEntity<List<ReporteUsuarioActivoDTO>> getUsuariosActivos(
            @RequestParam("fechaDesde") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaDesde,
            @RequestParam("fechaHasta") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaHasta,
            @RequestParam("tipoUsuario") String tipoUsuario
    ) {
        List<ReporteUsuarioActivoDTO> resultado = servicio.obtenerUsuariosQueMasViajan(tipoUsuario, fechaDesde, fechaHasta);
        return ResponseEntity.ok(resultado);
    }
}
