package org.example.msvcadmin.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.example.msvcadmin.entities.Admin;
import org.example.msvcadmin.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Operation(summary = "Asignar rol de administrador", description = "Asigna el rol de administrador a un usuario por su ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Rol de administrador asignado correctamente"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/usuarios/{userId}")
    public ResponseEntity<Admin> asignarAdmin(
            @Parameter(description = "ID del usuario") @PathVariable String userId) {
        return ResponseEntity.ok(adminService.asignarRolAdmin(userId));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Quitar rol de administrador", description = "Quita el rol de administrador a un usuario por su ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Rol de administrador quitado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @DeleteMapping("/usuarios/{userId}")
    public ResponseEntity<Void> quitarAdmin(
            @Parameter(description = "ID del usuario")@PathVariable String userId) {
        adminService.quitarRolAdmin(userId);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Obtener administrador", description = "Obtiene un administrador por su ID de usuario.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Administrador encontrado"),
            @ApiResponse(responseCode = "404", description = "Administrador no encontrado")
    })
    @GetMapping("/usuarios/{userId}")
    public ResponseEntity<Admin> obtenerAdmin(
            @Parameter(description = "ID del usuario") @PathVariable String userId) {
        return adminService.obtenerPorUserId(userId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Listar administradores", description = "Lista todos los administradores del sistema. Puede filtrar por si están activos o no.")
    @ApiResponse(responseCode = "200", description = "Lista de administradores")
    @GetMapping("/usuarios")
    public ResponseEntity<List<Admin>> listarAdmins(
            @Parameter(description = "Filtrar por estado de actividad")
            @RequestParam(required = false) Boolean activo) {
        return ResponseEntity.ok(adminService.litarTodosAdmin(activo));
    }
}
