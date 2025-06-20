package org.example.msvcreporte.controllers;

import org.example.msvcreporte.dto.ReporteFacturacionPeriodoDTO;
import org.example.msvcreporte.services.ReporteFacturacionPeriodoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reporte")
public class ReporteFacturacionController {

    private final ReporteFacturacionPeriodoService servicio;

    public ReporteFacturacionController(ReporteFacturacionPeriodoService servicio) {
        this.servicio = servicio;
    }

    @GetMapping("/facturacion-total")
    public ResponseEntity<ReporteFacturacionPeriodoDTO> facturacionTotal(
            @RequestParam int anio,
            @RequestParam int mesDesde,
            @RequestParam int mesHasta) {

        ReporteFacturacionPeriodoDTO dto = servicio.generarReporte(anio, mesDesde, mesHasta);
        return ResponseEntity.ok(dto);
    }
}
