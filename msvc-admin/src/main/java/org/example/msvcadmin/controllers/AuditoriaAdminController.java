package org.example.msvcadmin.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.example.msvcadmin.entities.AuditoriaAdmin;
import org.example.msvcadmin.repositories.AuditoriaAdminRepository;
import org.example.msvcadmin.services.AuditoriaAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/admin/auditoria")
public class AuditoriaAdminController {

    @Autowired
    private AuditoriaAdminService auditoriaAdminService;

    @Operation(summary = "Listar auditorías", description = "Devuelve una lista de auditorías realizadas por administradores. Se puede filtrar por ID de admin, tipo de acción y fechas.")
    @ApiResponse(responseCode = "200", description = "Lista de auditorías")
    @GetMapping
    public ResponseEntity<List<AuditoriaAdmin>> listarAuditoria(
            @Parameter(description = "ID del administrador (opcional)")
            @RequestParam(required = false) UUID adminId,

            @Parameter(description = "Tipo de acción realizada (opcional)")
            @RequestParam(required = false) String tipoAccion,

            @Parameter(description = "Fecha desde (formato ISO 8601)")
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaDesde,

            @Parameter(description = "Fecha hasta (formato ISO 8601)")
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaHasta
    ) {
        List<AuditoriaAdmin> resultados = auditoriaAdminService.buscarConFiltros(adminId, tipoAccion, fechaDesde, fechaHasta);
        return ResponseEntity.ok(resultados);
    }

    @Operation(summary = "Obtener auditoría por ID", description = "Obtiene los detalles de una acción administrativa específica mediante su UUID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Auditoría encontrada"),
            @ApiResponse(responseCode = "404", description = "Auditoría no encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<AuditoriaAdmin> obtenerAuditoriaPorId(
            @Parameter(description = "UUID de la auditoría")@PathVariable UUID id) {
        return auditoriaAdminService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

}
