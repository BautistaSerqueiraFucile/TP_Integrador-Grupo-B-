package org.example.msvcreporte.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.example.msvcreporte.dto.ReporteUsoMonopatinDTO;
import org.example.msvcreporte.services.ReporteUsoMonopatinService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;

@Tag(name = "Reporte - Uso General de Monopatines", description = "Estadísticas generales de uso de monopatines")
@RestController
@RequestMapping("/reporte")
public class ReporteUsoMonopatinController {

    private final ReporteUsoMonopatinService reporteService;

    public ReporteUsoMonopatinController(ReporteUsoMonopatinService reporteService) {
        this.reporteService = reporteService;
    }

    @Operation(
            summary = "Obtener uso general de monopatines",
            description = "Devuelve estadísticas generales de uso de todos los monopatines registrados en el sistema."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reporte generado exitosamente")
    })
    @GetMapping("/uso-monopatines")
    public ResponseEntity<List<ReporteUsoMonopatinDTO>> getUsoMonopatines() {
        List<ReporteUsoMonopatinDTO> resultado = reporteService.generarReporte();
        return ResponseEntity.ok(resultado);
    }
}
