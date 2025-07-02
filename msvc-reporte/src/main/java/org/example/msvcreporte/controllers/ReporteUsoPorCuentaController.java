package org.example.msvcreporte.controllers;

import org.example.msvcreporte.dto.ReporteUsoPorCuentaDTO;
import org.example.msvcreporte.models.Viaje;
import org.example.msvcreporte.services.ReporteUsoPorCuentaService;
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
public class ReporteUsoPorCuentaController {

    private final ReporteUsoPorCuentaService reporteUsoPorCuentaService;

    public ReporteUsoPorCuentaController(ReporteUsoPorCuentaService reporteUsoPorCuentaService) {
        this.reporteUsoPorCuentaService = reporteUsoPorCuentaService;
    }

    @GetMapping("/uso-por-cuenta")
    public ResponseEntity<List<Viaje>> getUsoPorCuenta(
            @RequestParam("idCuenta") Long idCuenta,
            @RequestParam("fechaDesde") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaDesde,
            @RequestParam("fechaHasta") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaHasta
    ) {
        return ResponseEntity.ok(reporteUsoPorCuentaService.generarReporte(idCuenta, fechaDesde, fechaHasta));
    }
}
