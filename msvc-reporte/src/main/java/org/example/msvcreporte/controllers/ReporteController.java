package org.example.msvcreporte.controllers;

import org.example.msvcreporte.entities.Reporte;
import org.example.msvcreporte.services.ReporteService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/reporte")
public class ReporteController {
    private final ReporteService reporteService;

    public ReporteController(ReporteService reporteService) {
        this.reporteService = reporteService;
    }

    @GetMapping("/historial")
    public List<Reporte> historial() {
        return reporteService.obtenerTodos();
    }

}
