package org.example.msvcadmin.controllers;

import org.example.msvcadmin.services.AccionAdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin")
public class AccionAdminController {
    private final AccionAdminService accionAdminService;

    public AccionAdminController(AccionAdminService accionAdminService) {
        this.accionAdminService = accionAdminService;
    }

    @PutMapping("/cuentas/{cuentaId}/bloquear")
    public ResponseEntity<Void> bloquearCuenta(
            @PathVariable Long cuentaId,
            @RequestParam String userIdAdmin // 👈 viene como parámetro de la URL
    ) {
        accionAdminService.bloquearCuenta(cuentaId, userIdAdmin); // 👈 AQUÍ VA
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/cuentas/{cuentaId}/activar")
    public ResponseEntity<Void> reactivarCuenta(
            @PathVariable Long cuentaId,
            @RequestParam String userIdAdmin
    ) {
        accionAdminService.reactivarCuenta(cuentaId, userIdAdmin);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/scooters/{scooterId}/estado/{estado}")
    public ResponseEntity<Void> cambiarEstadoScooter(
            @PathVariable Long scooterId,
            @PathVariable String estado,
            @RequestParam String userIdAdmin
    ) {
        accionAdminService.cambiarEstadoScooter(scooterId, estado, userIdAdmin);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/monopatines")
    public ResponseEntity<Void> agregarMonopatin(
            @RequestBody Map<String, Object> datos,
            @RequestParam String userIdAdmin
    ) {
        accionAdminService.agregarMonopatin(datos, userIdAdmin);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/monopatines/{id}")
    public ResponseEntity<Void> eliminarMonopatin(
            @PathVariable Long id,
            @RequestParam String userIdAdmin
    ) {
        accionAdminService.eliminarMonopatin(id, userIdAdmin);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/paradas")
    public ResponseEntity<Void> crearParada(
            @RequestBody Map<String, Object> datos,
            @RequestParam String userIdAdmin
    ) {
        accionAdminService.crearParada(datos, userIdAdmin);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/paradas/{id}")
    public ResponseEntity<Void> editarParada(
            @PathVariable Long id,
            @RequestBody Map<String, Object> datos,
            @RequestParam String userIdAdmin
    ) {
        accionAdminService.editarParada(id, datos, userIdAdmin);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/paradas/{id}")
    public ResponseEntity<Void> eliminarParada(
            @PathVariable Long id,
            @RequestParam String userIdAdmin
    ) {
        accionAdminService.eliminarParada(id, userIdAdmin);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/tarifas")
    public ResponseEntity<Void> crearTarifa(
            @RequestBody Map<String, Object> datos,
            @RequestParam String userIdAdmin
    ) {
        accionAdminService.crearTarifa(datos, userIdAdmin);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/tarifas/{id}")
    public ResponseEntity<Void> modificarTarifa(
            @PathVariable Long id,
            @RequestBody Map<String, Object> datos,
            @RequestParam String userIdAdmin
    ) {
        accionAdminService.modificarTarifa(id, datos, userIdAdmin);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/tarifas-extra")
    public ResponseEntity<Void> crearTarifaExtra(
            @RequestBody Map<String, Object> datos,
            @RequestParam String userIdAdmin
    ) {
        accionAdminService.crearTarifaExtra(datos, userIdAdmin);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/tarifas-extra/{id}")
    public ResponseEntity<Void> modificarTarifaExtra(
            @PathVariable Long id,
            @RequestBody Map<String, Object> datos,
            @RequestParam String userIdAdmin
    ) {
        accionAdminService.modificarTarifaExtra(id, datos, userIdAdmin);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/reportes/usuarios-top")
    public ResponseEntity<List<Map<String, Object>>> usuariosTop(
            @RequestParam String fechaDesde,
            @RequestParam String fechaHasta,
            @RequestParam String tipoUsuario,
            @RequestParam String userIdAdmin
    ) {
        List<Map<String, Object>> resultado = accionAdminService.consultarUsuariosTop(fechaDesde, fechaHasta, tipoUsuario, userIdAdmin);
        return ResponseEntity.ok(resultado);
    }

    @GetMapping("/reportes/uso-monopatines")
    public ResponseEntity<List<Map<String, Object>>> usoMonopatines(
            @RequestParam boolean incluirPausas,
            @RequestParam String userIdAdmin
    ) {
        List<Map<String, Object>> resultado = accionAdminService.consultarUsoMonopatines(incluirPausas, userIdAdmin);
        return ResponseEntity.ok(resultado);
    }

    @GetMapping("/reportes/monopatines-frecuentes")
    public ResponseEntity<List<Map<String, Object>>> monopatinesFrecuentes(
            @RequestParam int anio,
            @RequestParam int minViajes,
            @RequestParam String userIdAdmin
    ) {
        List<Map<String, Object>> resultado = accionAdminService.consultarMonopatinesFrecuentes(anio, minViajes, userIdAdmin);
        return ResponseEntity.ok(resultado);
    }

    @GetMapping("/reportes/facturacion-total")
    public ResponseEntity<Map<String, Object>> facturacionTotal(
            @RequestParam int anio,
            @RequestParam int mesDesde,
            @RequestParam int mesHasta,
            @RequestParam String userIdAdmin
    ) {
        Map<String, Object> resultado = accionAdminService.consultarFacturacionTotal(anio, mesDesde, mesHasta, userIdAdmin);
        return ResponseEntity.ok(resultado);
    }
}
