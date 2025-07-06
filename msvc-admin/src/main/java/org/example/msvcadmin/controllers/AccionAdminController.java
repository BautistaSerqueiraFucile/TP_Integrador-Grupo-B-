package org.example.msvcadmin.controllers;

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
    @PutMapping("/cuentas/{cuentaId}/bloquear")
    public ResponseEntity<Void> bloquearCuenta(
            @PathVariable Long cuentaId,
            @RequestParam String userIdAdmin // 👈 viene como parámetro de la URL
    ) {
        accionAdminService.bloquearCuenta(cuentaId, userIdAdmin); // 👈 AQUÍ VA
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/cuentas/{cuentaId}/activar")
    public ResponseEntity<Void> reactivarCuenta(
            @PathVariable Long cuentaId,
            @RequestParam String userIdAdmin
    ) {
        accionAdminService.reactivarCuenta(cuentaId, userIdAdmin);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/monopatines/{scooterId}/estado/{estado}")
    public ResponseEntity<Void> cambiarEstadoScooter(
            @PathVariable Long scooterId,
            @PathVariable String estado,
            @RequestParam String userIdAdmin
    ) {
        accionAdminService.cambiarEstadoScooter(scooterId, estado, userIdAdmin);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/monopatines")
    public ResponseEntity<Void> agregarMonopatin(
            @RequestBody Monopatin datos,
            @RequestParam String userIdAdmin
    ) {
        accionAdminService.agregarMonopatin(datos, userIdAdmin);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/monopatines/{id}")
    public ResponseEntity<Void> eliminarMonopatin(
            @PathVariable Long id,
            @RequestParam String userIdAdmin
    ) {
        accionAdminService.eliminarMonopatin(id, userIdAdmin);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/paradas")
    public ResponseEntity<Void> crearParada(
            @RequestBody Parada datos,
            @RequestParam String userIdAdmin
    ) {
        accionAdminService.crearParada(datos, userIdAdmin);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
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
    @DeleteMapping("/paradas/{id}")
    public ResponseEntity<Void> eliminarParada(
            @PathVariable Long id,
            @RequestParam String userIdAdmin
    ) {
        accionAdminService.eliminarParada(id, userIdAdmin);
        return ResponseEntity.noContent().build();
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/tarifa/{userAdmin}")
    public ResponseEntity<Tarifa> modificarTarifa(@RequestBody Tarifa tarifa, @PathVariable String userAdmin) {
        accionAdminService.modificarTarifa(tarifa, userAdmin);
        return ResponseEntity.noContent().build();
    }


    @PreAuthorize("hasRole('ADMIN')")
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
    @GetMapping("/reportes/uso-monopatines")
    public ResponseEntity<List<ReporteUsoMonopatinDTO>> usoMonopatines(
            @RequestParam String userIdAdmin
    ) {
        ResponseEntity<List<ReporteUsoMonopatinDTO>> resultado = accionAdminService.consultarUsoMonopatines( userIdAdmin);
        return ResponseEntity.ok(resultado.getBody());
    }

    @PreAuthorize("hasRole('ADMIN')")
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
    @GetMapping("/reportes/facturacion-total")
    public double facturacionTotal(
            @RequestParam LocalDate fechaDesde,
            @RequestParam LocalDate fechaHasta,
            @RequestParam String userIdAdmin
    ) {

        return accionAdminService.consultarFacturacionTotal(fechaDesde, fechaHasta, userIdAdmin);
    }

}
