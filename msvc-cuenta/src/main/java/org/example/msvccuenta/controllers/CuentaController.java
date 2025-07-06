package org.example.msvccuenta.controllers;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;
import org.example.msvccuenta.entities.Cuenta;
import org.example.msvccuenta.entities.TipoCuenta;
import org.example.msvccuenta.entities.dto.ParadaDto;
import org.example.msvccuenta.exceptions.CuentaNoEncontradaException;
import org.example.msvccuenta.services.CuentaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Tag(name = "Cuentas", description = "Operaciones sobre cuentas")

@RestController
@RequestMapping("/cuentas")
public class CuentaController {
    private final CuentaService cuentaService;

    public CuentaController(CuentaService cuentaService) {
        this.cuentaService = cuentaService;
    }

    @GetMapping("")
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

    @PostMapping("")
    public ResponseEntity<?> crear(@Valid @RequestBody Cuenta cuenta, BindingResult result) {
        if (result.hasErrors()) {
            return validar(result);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(cuentaService.crear(cuenta));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@Valid @RequestBody Cuenta cuenta, BindingResult result, @PathVariable String id) {
        if (result.hasErrors()) {
            return validar(result);
        }
        try {
            Long cuentaId = Long.parseLong(id);
            Cuenta cuentaActualizada = cuentaService.actualizar(cuentaId, cuenta);
            return ResponseEntity.ok(cuentaActualizada);
        } catch (NumberFormatException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"El ID debe ser un número válido.\"}");
        } catch (CuentaNoEncontradaException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable String id) {
        try {
            Long cuentaId = Long.parseLong(id);
            cuentaService.eliminar(cuentaId);
            return ResponseEntity.noContent().build();
        } catch (NumberFormatException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"El ID debe ser un número válido.\"}");
        } catch (CuentaNoEncontradaException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

    @PutMapping("/anular/{id}")
    public ResponseEntity<?> anular(@PathVariable String id) {
        try {
            Long cuentaId = Long.parseLong(id);
            Cuenta cuentaAnulada = cuentaService.anularCuenta(cuentaId);
            return ResponseEntity.ok(cuentaAnulada);
        } catch (NumberFormatException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"El ID debe ser un número válido.\"}");
        } catch (CuentaNoEncontradaException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

    @PutMapping("/activar/{id}")
    public ResponseEntity<?> activar(@PathVariable String id) {
        try {
            Long cuentaId = Long.parseLong(id);
            Cuenta cuentaActivada = cuentaService.activarCuenta(cuentaId);
            return ResponseEntity.ok(cuentaActivada);
        } catch (NumberFormatException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"El ID debe ser un número válido.\"}");
        } catch (CuentaNoEncontradaException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

    @GetMapping("/saldo/{id}")
    public ResponseEntity<?> obtenerSaldo(@PathVariable String id) {
        try {
            Long cuentaId = Long.parseLong(id);
            Double saldo = cuentaService.obtenerSaldo(cuentaId);
            return ResponseEntity.ok(saldo);
        } catch (NumberFormatException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"El ID debe ser un número válido.\"}");
        } catch (CuentaNoEncontradaException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

    @PutMapping("/{id}/set-plan/{tipo}")
    public ResponseEntity<?> setPlan(@PathVariable String id, @PathVariable TipoCuenta tipo) {
        try {
            Long cuentaId = Long.parseLong(id);
            Cuenta cuentaActualizada = cuentaService.actualizarTipoCuenta(cuentaId, tipo);
            return ResponseEntity.ok(cuentaActualizada);
        } catch (NumberFormatException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"El ID debe ser un número válido.\"}");
        } catch (CuentaNoEncontradaException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"" + e.getMessage() + "\"}");
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


    @PutMapping("/recargar/{id}/monto/{monto}")
    public ResponseEntity<?> recargarSaldo(@PathVariable Long id, @PathVariable String monto) {
        try {
            try {
                Double.parseDouble(monto);
            } catch (NumberFormatException e) {
                return ResponseEntity.badRequest().body("{\"error\":\"El monto proporcionado no es un número válido.\"}");
            }
            Cuenta cuenta = cuentaService.recargarSaldo(id, Double.parseDouble(monto));
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

    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<?> obtenerPorTipo(@PathVariable String tipo) {
        try {
            List<?> cuentas = cuentaService.obtenerPorTipo(tipo.toUpperCase());
            return ResponseEntity.ok(cuentas);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

    @GetMapping("/{idCuenta}/parada-cercana")
    public ResponseEntity<?> obtenerParadaCercana(@PathVariable Long idCuenta) {
        try {
            ParadaDto paradaMasCercana = cuentaService.paradaMasCercana(idCuenta);
            return ResponseEntity.ok(paradaMasCercana);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }
}