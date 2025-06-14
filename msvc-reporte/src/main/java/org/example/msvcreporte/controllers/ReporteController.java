package org.example.msvcreporte.controllers;

import org.example.msvcreporte.services.ReporteService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reportes")
public class ReporteController {
    private final ReporteService reporteService;

    public ReporteController(ReporteService reporteService) {
        this.reporteService = reporteService;
    }

    @GetMapping(/usuarios/{id})
    Public Usuario getUsuarioId(id){
        return reporteService.getUsuarioId(id);
    }


}
