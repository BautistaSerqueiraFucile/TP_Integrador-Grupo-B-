package org.example.msvcreporte.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.example.msvcreporte.dto.ReporteMonopatinFrecuenteDTO;
import org.example.msvcreporte.models.MonopatinViajeDTO;
import org.example.msvcreporte.services.ReporteMonopatinesFrecuentesService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;

@Tag(name = "Reporte - Monopatines Frecuentes", description = "Ver cuáles monopatines se usan más")
@RestController
@RequestMapping("/reporte")
public class ReporteMonopatinesFrecuentesController {

    private final ReporteMonopatinesFrecuentesService servicio;

    public ReporteMonopatinesFrecuentesController(ReporteMonopatinesFrecuentesService servicio) {
        this.servicio = servicio;
    }

    @Operation(
            summary = "Obtener monopatines más usados",
            description = "Devuelve una lista de monopatines que superan un mínimo de viajes realizados en un año determinado."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de monopatines frecuentes obtenida correctamente"),
            @ApiResponse(responseCode = "400", description = "Parámetros inválidos")
    })
    @GetMapping("/monopatines-mas-usados")
    public ResponseEntity<List<MonopatinViajeDTO>> getMonopatinesFrecuentes(
            @Parameter(description = "Año de análisis", example = "2024")
            @RequestParam("anio") Integer anio,

            @Parameter(description = "Cantidad mínima de viajes para ser considerado frecuente", example = "50")
            @RequestParam("minViajes") Long minViajes
    ) {
        List<MonopatinViajeDTO> lista = servicio.generarReporte(anio, minViajes);
        return ResponseEntity.ok(lista);
    }

}
