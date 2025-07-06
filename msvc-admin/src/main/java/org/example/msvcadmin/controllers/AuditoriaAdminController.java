package org.example.msvcadmin.controllers;

import org.example.msvcadmin.entities.AuditoriaAdmin;
import org.example.msvcadmin.repositories.AuditoriaAdminRepository;
import org.example.msvcadmin.services.AuditoriaAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/admin/auditoria")
public class AuditoriaAdminController {

    @Autowired
    private AuditoriaAdminService auditoriaAdminService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<AuditoriaAdmin>> listarAuditoria(
            @RequestParam(required = false) UUID adminId,
            @RequestParam(required = false) String tipoAccion,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaDesde,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaHasta
    ) {
        List<AuditoriaAdmin> resultados = auditoriaAdminService.buscarConFiltros(adminId, tipoAccion, fechaDesde, fechaHasta);
        return ResponseEntity.ok(resultados);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<AuditoriaAdmin> obtenerAuditoriaPorId(@PathVariable UUID id) {
        return auditoriaAdminService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    /** Ejemplo de uso de los endpoints
     * 1. Listar todas las acciones:GET /admin/auditoria
     * 2. Filtrar por tipo de acción:GET /admin/auditoria?tipoAccion=bloquear_cuenta
     * 3. Filtrar por fecha:GET /admin/auditoria?fechaDesde=2025-06-01T00:00:00
     * 4. Obtener una acción específica:GET /admin/auditoria/f8c3b213-aaaa-4eaa-bbbb-1234567890ab
     */
}
