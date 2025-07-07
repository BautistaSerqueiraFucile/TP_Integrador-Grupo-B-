package org.example.msvcreporte.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.example.msvcreporte.dto.ReporteUsuarioActivoDTO;
import org.example.msvcreporte.services.ReporteUsuariosActivosService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.time.LocalDate;
import java.util.List;

@Tag(name = "Reporte - Usuarios Activos", description = "Obtener usuarios más activos por tipo y rango de fechas")
@RestController
@RequestMapping("/reporte")
public class ReporteUsuariosActivosController {

    private final ReporteUsuariosActivosService servicio;

    public ReporteUsuariosActivosController(ReporteUsuariosActivosService servicio) {
        this.servicio = servicio;
    }

    @Operation(
            summary = "Obtener usuarios más activos",
            description = "Devuelve una lista de los usuarios que más viajes realizaron en un período determinado, filtrando por tipo de usuario."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de usuarios obtenida correctamente"),
            @ApiResponse(responseCode = "400", description = "Parámetros inválidos")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/usuarios-top")
    public ResponseEntity<List<ReporteUsuarioActivoDTO>> getUsuariosActivos(
            @Parameter(description = "Fecha de inicio del período (formato yyyy-MM-dd)", example = "2024-01-01")
            @RequestParam("fechaDesde") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaDesde,

            @Parameter(description = "Fecha de fin del período (formato yyyy-MM-dd)", example = "2024-12-31")
            @RequestParam("fechaHasta") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaHasta,

            @Parameter(description = "Tipo de usuario (ej. REGULAR o PREMIUM)", example = "REGULAR")
            @RequestParam("tipoUsuario") String tipoUsuario
    ) {
        List<ReporteUsuarioActivoDTO> resultado = servicio.obtenerUsuariosQueMasViajan(tipoUsuario, fechaDesde, fechaHasta);
        return ResponseEntity.ok(resultado);
    }
}
