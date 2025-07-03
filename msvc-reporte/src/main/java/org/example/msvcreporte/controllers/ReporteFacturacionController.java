package org.example.msvcreporte.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.example.msvcreporte.dto.ReporteFacturacionPeriodoDTO;
import org.example.msvcreporte.services.ReporteFacturacionPeriodoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.time.LocalDate;

@Tag(name = "Reporte - Facturación", description = "Consultar facturación total entre fechas")
@RestController
@RequestMapping("/reporte")
public class ReporteFacturacionController {

    private final ReporteFacturacionPeriodoService servicio;

    public ReporteFacturacionController(ReporteFacturacionPeriodoService servicio) {
        this.servicio = servicio;
    }

    @Operation(
            summary = "Consultar facturación total",
            description = "Devuelve el monto total facturado en el sistema dentro de un rango de fechas específico."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Consulta exitosa de la facturación total"),
            @ApiResponse(responseCode = "400", description = "Parámetros inválidos")
    })
    @GetMapping("/facturacion-total")
    public double facturacionTotal(
            @Parameter(description = "Fecha de inicio del periodo (formato yyyy-MM-dd)", example = "2024-01-01")
            @RequestParam LocalDate fechaDesde,

            @Parameter(description = "Fecha de fin del periodo (formato yyyy-MM-dd)", example = "2024-12-31")
            @RequestParam LocalDate fechaHasta
    ) {
        return servicio.generarReporte(fechaDesde, fechaHasta);
    }
}
