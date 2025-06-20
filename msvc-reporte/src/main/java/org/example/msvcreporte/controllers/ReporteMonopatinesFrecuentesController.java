package org.example.msvcreporte.controllers;

import org.example.msvcreporte.dto.ReporteMonopatinFrecuenteDTO;
import org.example.msvcreporte.services.ReporteMonopatinesFrecuentesService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/reporte")
public class ReporteMonopatinesFrecuentesController {

    private final ReporteMonopatinesFrecuentesService servicio;

    public ReporteMonopatinesFrecuentesController(ReporteMonopatinesFrecuentesService servicio) {
        this.servicio = servicio;
    }

    @GetMapping("/monopatines-mas-usados")
    public ResponseEntity<List<ReporteMonopatinFrecuenteDTO>> getMonopatinesFrecuentes(
            @RequestParam("anio") int anio,
            @RequestParam("minViajes") int minViajes
    ) {
        List<ReporteMonopatinFrecuenteDTO> lista = servicio.generarReporte(anio, minViajes);
        return ResponseEntity.ok(lista);
    }

}
