package org.example.msvcreporte.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.msvcreporte.entities.Reporte;
import org.example.msvcreporte.services.ReporteService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Reporte - Historial General", description = "Obtener historial completo de reportes")
@RestController
@RequestMapping("/reporte")
public class ReporteController {

    private final ReporteService reporteService;

    public ReporteController(ReporteService reporteService) {
        this.reporteService = reporteService;
    }

    @Operation(
            summary = "Obtener historial de reportes",
            description = "Devuelve una lista con todos los reportes registrados en el sistema."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de reportes obtenida exitosamente")
    })
    @GetMapping("/historial")
    public List<Reporte> historial() {
        return reporteService.obtenerTodos();
    }

}
