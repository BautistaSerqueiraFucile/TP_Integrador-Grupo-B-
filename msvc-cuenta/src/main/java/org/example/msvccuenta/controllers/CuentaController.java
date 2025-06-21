package org.example.msvccuenta.controllers;

import jakarta.validation.Valid;
import org.example.msvccuenta.entities.Cuenta;
import org.example.msvccuenta.exceptions.CuentaNoEncontradaException;
import org.example.msvccuenta.services.CuentaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.example.msvccuenta.entities.TipoCuenta;
import java.util.HashMap;
import java.util.Map;

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
    public ResponseEntity<?> buscarPorId(@PathVariable String id) {
        try {
            Long cuentaId = Long.parseLong(id);
            Cuenta cuenta = cuentaService.buscarPorId(cuentaId);
            return ResponseEntity.ok(cuenta);
        } catch (NumberFormatException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"El ID debe ser un número válido.\"}");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"" + e.getMessage() + "\"}");
        } catch (CuentaNoEncontradaException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

    @PostMapping("/crear")
    public ResponseEntity<?> crear(@Valid @RequestBody Cuenta cuenta, BindingResult result) {
        if (result.hasErrors()) {
            return validar(result);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(cuentaService.crear(cuenta));
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

    @PatchMapping("/anular/{id}")
    public ResponseEntity<Cuenta> anular(@PathVariable Long id) {
        try {
            Cuenta cuenta = cuentaService.anularCuenta(id)
                    .orElseThrow(() -> new RuntimeException("Cuenta no encontrada"));
            return ResponseEntity.ok(cuenta);
        } catch (RuntimeException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/activar/{id}")
    public ResponseEntity<Cuenta> activar(@PathVariable Long id) {
        try {
            Cuenta cuenta = cuentaService.activarCuenta(id)
                    .orElseThrow(() -> new RuntimeException("Cuenta no encontrada"));
            return ResponseEntity.ok(cuenta);
        } catch (RuntimeException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/saldo/{id}")
    public ResponseEntity<BigDecimal> obtenerSaldo(@PathVariable Long id) {
        return cuentaService.obtenerSaldo(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}/set-plan/{tipo}")
    public ResponseEntity<Cuenta> setPlan(@PathVariable Long id, @PathVariable TipoCuenta tipo) {
        try {
            Cuenta cuenta = cuentaService.actualizarTipoCuenta(id, tipo)
                    .orElseThrow(() -> new RuntimeException("Cuenta no encontrada"));
            return ResponseEntity.ok(cuenta);
        } catch (RuntimeException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{idCuenta}/distancia-parada/{idParada}")
    public ResponseEntity<Double> calcularDistanciaAParada(@PathVariable Long idCuenta, @PathVariable Long idParada) {
        Double distancia = cuentaService.calcularDistanciaAParada(idCuenta, idParada);
        if (distancia == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(distancia);
    }

    @PatchMapping("/recargar/{id}/monto/{monto}")
    public ResponseEntity<?> recargarSaldo(@PathVariable Long id, @PathVariable String monto) { // Cambiado a ResponseEntity<?> para más flexibilidad
        try {
            BigDecimal montoDecimal;
            try {
                montoDecimal = new BigDecimal(monto);
            } catch (NumberFormatException e) {
                return ResponseEntity.badRequest().body("{\"error\":\"El monto proporcionado no es un número válido.\"}");
            }
            Cuenta cuenta = cuentaService.recargarSaldo(id, montoDecimal);
            return ResponseEntity.ok(cuenta);
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("{\"error\":\"" + e.getMessage() + "\"}");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("{\"error\":\"" + e.getMessage() + "\"}");
        } catch (RuntimeException e) {
            if (e.getMessage() != null && e.getMessage().startsWith("Cuenta no encontrada")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"" + e.getMessage() + "\"}");
            }
            return ResponseEntity.badRequest().body("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

    @GetMapping("/viajes/{id}")
    public ResponseEntity<?> getViajesPorUsuarioYPeriodo(@PathVariable Long id) {
        try {
            List<?> viajes = cuentaService.historialViajes(id);
            return ResponseEntity.ok(viajes);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }
    private static ResponseEntity<Map<String, String>> validar(BindingResult result) {
        Map<String, String> errores = new HashMap<>();
        result.getFieldErrors().forEach(err -> {
            errores.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errores);
    }
}