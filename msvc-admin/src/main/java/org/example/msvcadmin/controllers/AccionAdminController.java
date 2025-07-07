package org.example.msvcadmin.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.example.msvcadmin.models.*;
import org.example.msvcadmin.services.AccionAdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin")
public class AccionAdminController {
    private final AccionAdminService accionAdminService;

    public AccionAdminController(AccionAdminService accionAdminService) {
        this.accionAdminService = accionAdminService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Bloquear cuenta",
            description = "Bloquea una cuenta existente. Solo puede ser realizada por un administrador autorizado."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Cuenta bloqueada con éxito"),
            @ApiResponse(responseCode = "400", description = "Parámetros inválidos"),
            @ApiResponse(responseCode = "404", description = "Cuenta no encontrada")
    })
    @PutMapping("/cuentas/{cuentaId}/bloquear")
    public ResponseEntity<Void> bloquearCuenta(
            @Parameter(description = "ID de la cuenta a bloquear") @PathVariable Long cuentaId,
            @Parameter(description = "ID del administrador que realiza la acción") @RequestParam String userIdAdmin // 👈 viene como parámetro de la URL
    ) {
        accionAdminService.bloquearCuenta(cuentaId, userIdAdmin); // 👈 AQUÍ VA
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/cuentas/{cuentaId}/activar")
    @Operation(summary = "Reactivar cuenta", description = "Reactiva una cuenta bloqueada.")
    @PutMapping("/cuentas/{cuentaId}/activar")
    public ResponseEntity<Void> reactivarCuenta(
            @Parameter(description = "ID de la cuenta a reactivar") @PathVariable Long cuentaId,
            @Parameter(description = "ID del administrador que realiza la acción") @RequestParam String userIdAdmin
    ) {
        accionAdminService.reactivarCuenta(cuentaId, userIdAdmin);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Cambiar estado de monopatín", description = "Cambia el estado de un monopatín (activo, fuera de servicio, etc.).")
    @PutMapping("/monopatines/{scooterId}/estado/{estado}")
    public ResponseEntity<Void> cambiarEstadoScooter(
            @Parameter(description = "ID del monopatín") @PathVariable Long scooterId,
            @Parameter(description = "Nuevo estado del monopatín") @PathVariable String estado,
            @Parameter(description = "ID del administrador") @RequestParam String userIdAdmin
    ) {
        accionAdminService.cambiarEstadoScooter(scooterId, estado, userIdAdmin);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Agregar monopatín", description = "Agrega un nuevo monopatín al sistema.")
    @PostMapping("/monopatines")
    public ResponseEntity<Void> agregarMonopatin(
            @RequestBody Monopatin datos,
            @RequestParam String userIdAdmin
    ) {
        accionAdminService.agregarMonopatin(datos, userIdAdmin);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Eliminar monopatín", description = "Elimina un monopatín existente.")
    @DeleteMapping("/monopatines/{id}")
    public ResponseEntity<Void> eliminarMonopatin(
            @PathVariable Long id,
            @RequestParam String userIdAdmin
    ) {
        accionAdminService.eliminarMonopatin(id, userIdAdmin);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Crear parada", description = "Crea una nueva parada para monopatines.")
    @PostMapping("/paradas")
    public ResponseEntity<Void> crearParada(
            @RequestBody Parada datos,
            @RequestParam String userIdAdmin
    ) {
        accionAdminService.crearParada(datos, userIdAdmin);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Editar parada", description = "Edita una parada existente.")
    @PutMapping("/paradas/{id}")
    public ResponseEntity<Void> editarParada(
            @PathVariable Long id,
            @RequestBody Parada datos,
            @RequestParam String userIdAdmin
    ) {
        accionAdminService.editarParada(id, datos, userIdAdmin);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Eliminar parada", description = "Elimina una parada existente.")
    @DeleteMapping("/paradas/{id}")
    public ResponseEntity<Void> eliminarParada(
            @PathVariable Long id,
            @RequestParam String userIdAdmin
    ) {
        accionAdminService.eliminarParada(id, userIdAdmin);
        return ResponseEntity.noContent().build();
    }


    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Modificar tarifa", description = "Modifica los valores de una tarifa existente.")
    @PutMapping("/tarifa/{userAdmin}")
    public ResponseEntity<Tarifa> modificarTarifa(
            @RequestBody Tarifa tarifa,
            @PathVariable String userAdmin) {
        accionAdminService.modificarTarifa(tarifa, userAdmin);
        return ResponseEntity.noContent().build();
    }


    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Obtener usuarios más activos", description = "Obtiene una lista de usuarios más activos en un periodo y tipo específico.")
    @GetMapping("/reportes/usuarios-top")
    public ResponseEntity<List<ReporteUsuarioActivoDTO>> usuariosTop(
            @RequestParam String fechaDesde,
            @RequestParam String fechaHasta,
            @RequestParam String tipoUsuario,
            @RequestParam String userIdAdmin
    ) {
        ResponseEntity<List<ReporteUsuarioActivoDTO>> resultado = accionAdminService.consultarUsuariosTop(fechaDesde, fechaHasta, tipoUsuario, userIdAdmin);
        return ResponseEntity.ok(resultado.getBody());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Obtener reporte de uso de monopatines", description = "Consulta el uso general de los monopatines.")
    @GetMapping("/reportes/uso-monopatines")
    public ResponseEntity<List<ReporteUsoMonopatinDTO>> usoMonopatines(
            @RequestParam String userIdAdmin
    ) {
        ResponseEntity<List<ReporteUsoMonopatinDTO>> resultado = accionAdminService.consultarUsoMonopatines( userIdAdmin);
        return ResponseEntity.ok(resultado.getBody());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Monopatines más usados", description = "Obtiene los monopatines con más viajes en un año.")
    @GetMapping("/reportes/monopatines-frecuentes")
    public ResponseEntity<List<MonopatinViajeDTO>> monopatinesFrecuentes(
            @RequestParam int anio,
            @RequestParam Long minViajes,
            @RequestParam String userIdAdmin
    ) {
        ResponseEntity<List<MonopatinViajeDTO>> resultado = accionAdminService.consultarMonopatinesFrecuentes(anio, minViajes, userIdAdmin);
        return ResponseEntity.ok(resultado.getBody());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Facturación total", description = "Consulta la facturación total del sistema en un periodo dado.")
    @GetMapping("/reportes/facturacion-total")
    public double facturacionTotal(
            @RequestParam LocalDate fechaDesde,
            @RequestParam LocalDate fechaHasta,
            @RequestParam String userIdAdmin
    ) {
        return accionAdminService.consultarFacturacionTotal(fechaDesde, fechaHasta, userIdAdmin);
    }

}
