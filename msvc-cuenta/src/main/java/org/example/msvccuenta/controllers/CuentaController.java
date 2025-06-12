package org.example.msvccuenta.controllers;

import org.example.msvccuenta.entities.Cuenta;
import org.example.msvccuenta.services.CuentaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/cuentas")
public class CuentaController {
    private final CuentaService cuentaService;

    public CuentaController(CuentaService cuentaService) {
        this.cuentaService = cuentaService;
    }

    @GetMapping("/")
    public List<Cuenta> listar() {
        return cuentaService.listar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cuenta> buscarPorId(@PathVariable Long id) {
        return cuentaService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/")
    public Cuenta crear(@RequestBody Cuenta cuenta) {
        return cuentaService.crear(cuenta);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cuenta> actualizar(@PathVariable Long id, @RequestBody Cuenta cuenta) {
        return cuentaService.actualizar(id, cuenta)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (cuentaService.eliminar(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
    @PutMapping("/anular/{id}")
    public ResponseEntity<Cuenta> anular(@PathVariable Long id) {
        return cuentaService.anularCuenta(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/activar/{id}")
    public ResponseEntity<Cuenta> activar(@PathVariable Long id) {
        return cuentaService.activarCuenta(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @GetMapping("/saldo/{id}")
    public ResponseEntity<BigDecimal> obtenerSaldo(@PathVariable Long id) {
        return cuentaService.obtenerSaldo(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}