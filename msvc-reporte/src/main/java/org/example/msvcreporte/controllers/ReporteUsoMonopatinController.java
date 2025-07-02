package org.example.msvcreporte.controllers;

import org.example.msvcreporte.dto.ReporteUsoMonopatinDTO;
import org.example.msvcreporte.services.ReporteUsoMonopatinService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/reporte")
public class ReporteUsoMonopatinController {

    private final ReporteUsoMonopatinService reporteService;

    public ReporteUsoMonopatinController(ReporteUsoMonopatinService reporteService) {
        this.reporteService = reporteService;
    }

    @GetMapping("/uso-monopatines")
    public ResponseEntity<List<ReporteUsoMonopatinDTO>> getUsoMonopatines()
            {
        List<ReporteUsoMonopatinDTO> resultado = reporteService.generarReporte();
        return ResponseEntity.ok(resultado);
    }
}
