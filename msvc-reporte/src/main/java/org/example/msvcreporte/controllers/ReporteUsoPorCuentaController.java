package org.example.msvcreporte.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Reporte - Uso por Cuenta", description = "Reportes detallados de viajes por cuenta entre fechas")
@RestController
@RequestMapping("/reporte")
public class ReporteUsoPorCuentaController {

    private final ReporteUsoPorCuentaService reporteUsoPorCuentaService;

    public ReporteUsoPorCuentaController(ReporteUsoPorCuentaService reporteUsoPorCuentaService) {
        this.reporteUsoPorCuentaService = reporteUsoPorCuentaService;
    }

    @Operation(
            summary = "Obtener viajes por cuenta",
            description = "Devuelve todos los viajes realizados por una cuenta dentro de un rango de fechas específico."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de viajes obtenida correctamente"),
            @ApiResponse(responseCode = "400", description = "Parámetros inválidos"),
            @ApiResponse(responseCode = "404", description = "Cuenta no encontrada o sin viajes")
    })
    @GetMapping("/uso-por-cuenta")
    public ResponseEntity<List<Viaje>> getUsoPorCuenta(
            @Parameter(description = "ID de la cuenta")
            @RequestParam("idCuenta") Long idCuenta,

            @Parameter(description = "Fecha de inicio del período (formato yyyy-MM-dd)", example = "2024-01-01")
            @RequestParam("fechaDesde")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaDesde,

            @Parameter(description = "Fecha de fin del período (formato yyyy-MM-dd)", example = "2024-12-31")
            @RequestParam("fechaHasta")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaHasta
    ) {
        return ResponseEntity.ok(reporteUsoPorCuentaService.generarReporte(idCuenta, fechaDesde, fechaHasta));
    }
}
