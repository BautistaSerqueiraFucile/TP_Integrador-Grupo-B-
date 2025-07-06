package org.example.msvccuenta.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

@Tag(name = "Cuentas", description = "API para la gestión de cuentas de usuarios")
@RestController
@RequestMapping("/cuentas")
public class CuentaController {
    private final CuentaService cuentaService;

    public CuentaController(CuentaService cuentaService) {
        this.cuentaService = cuentaService;
    }

    @Operation(summary = "Listar todas las cuentas", description = "Devuelve una lista con todas las cuentas existentes.")
    @ApiResponse(responseCode = "200", description = "Lista de cuentas obtenida exitosamente.")
    @GetMapping("")
    public List<Cuenta> listar() {
        return cuentaService.listar();
    }

    @Operation(summary = "Buscar una cuenta por su ID", description = "Obtiene los detalles de una cuenta específica a través de su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cuenta encontrada exitosamente.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Cuenta.class))),
            @ApiResponse(responseCode = "400", description = "El ID proporcionado no es un número válido.",
                    content = @Content(mediaType = "application/json", schema = @Schema(example = "{\"error\":\"El ID debe ser un número válido.\"}"))),
            @ApiResponse(responseCode = "404", description = "La cuenta con el ID especificado no fue encontrada.",
                    content = @Content(mediaType = "application/json", schema = @Schema(example = "{\"error\":\"Cuenta no encontrada con ID: 999\"}")))
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@Parameter(description = "ID de la cuenta a buscar.", required = true) @PathVariable String id) {
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

    @Operation(summary = "Crear una nueva cuenta", description = "Registra una nueva cuenta en el sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cuenta creada exitosamente.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Cuenta.class))),
            @ApiResponse(responseCode = "400", description = "Datos de la cuenta inválidos debido a errores de validación.",
                    content = @Content(mediaType = "application/json", schema = @Schema(example = "{\"saldo\":\"El campo saldo no puede ser nulo\"}")))
    })
    @PostMapping("")
    public ResponseEntity<?> crear(@Valid @RequestBody Cuenta cuenta, BindingResult result) {
        if (result.hasErrors()) {
            return validar(result);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(cuentaService.crear(cuenta));
    }

    @Operation(summary = "Actualizar una cuenta existente", description = "Modifica los datos de una cuenta existente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cuenta actualizada exitosamente.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Cuenta.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos o ID no numérico."),
            @ApiResponse(responseCode = "404", description = "Cuenta no encontrada.")
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@Valid @RequestBody Cuenta cuenta, BindingResult result, @Parameter(description = "ID de la cuenta a actualizar.", required = true) @PathVariable String id) {
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


    @Operation(summary = "Eliminar una cuenta por su ID", description = "Elimina permanentemente una cuenta del sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Cuenta eliminada exitosamente."),
            @ApiResponse(responseCode = "400", description = "El ID proporcionado no es un número válido."),
            @ApiResponse(responseCode = "404", description = "Cuenta no encontrada.")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@Parameter(description = "ID de la cuenta a eliminar.", required = true) @PathVariable String id) {
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

    @Operation(summary = "Anular el estado de una cuenta", description = "Cambia el estado de una cuenta a 'ANULADA'.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cuenta anulada exitosamente.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Cuenta.class))),
            @ApiResponse(responseCode = "400", description = "El ID proporcionado no es un número válido."),
            @ApiResponse(responseCode = "404", description = "Cuenta no encontrada.")
    })
    @PutMapping("/anular/{id}")
    public ResponseEntity<?> anular(@Parameter(description = "ID de la cuenta a anular.", required = true) @PathVariable String id) {
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

    @Operation(summary = "Activar el estado de una cuenta", description = "Cambia el estado de una cuenta a 'ACTIVA'.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cuenta activada exitosamente.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Cuenta.class))),
            @ApiResponse(responseCode = "400", description = "El ID proporcionado no es un número válido."),
            @ApiResponse(responseCode = "404", description = "Cuenta no encontrada.")
    })
    @PutMapping("/activar/{id}")
    public ResponseEntity<?> activar(@Parameter(description = "ID de la cuenta a activar.", required = true) @PathVariable String id) {
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

    @Operation(summary = "Obtener el saldo de una cuenta", description = "Devuelve el saldo actual de una cuenta específica.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Saldo obtenido exitosamente.",
                    content = @Content(mediaType = "application/json", schema = @Schema(example = "15000.0"))),
            @ApiResponse(responseCode = "400", description = "El ID proporcionado no es un número válido."),
            @ApiResponse(responseCode = "404", description = "Cuenta no encontrada.")
    })
    @GetMapping("/saldo/{id}")
    public ResponseEntity<?> obtenerSaldo(@Parameter(description = "ID de la cuenta.", required = true) @PathVariable String id) {
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

    @Operation(summary = "Establecer el tipo de plan de una cuenta", description = "Permite cambiar el tipo de cuenta entre BASICA y PREMIUM.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tipo de cuenta actualizado exitosamente.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Cuenta.class))),
            @ApiResponse(responseCode = "400", description = "El ID proporcionado no es un número válido."),
            @ApiResponse(responseCode = "404", description = "Cuenta no encontrada.")
    })
    @PutMapping("/{id}/set-plan/{tipo}")
    public ResponseEntity<?> setPlan(@Parameter(description = "ID de la cuenta.", required = true) @PathVariable String id,
                                     @Parameter(description = "Tipo de cuenta a establecer (BASICA o PREMIUM).", required = true) @PathVariable TipoCuenta tipo) {
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

    @Operation(summary = "Calcular distancia a una parada", description = "Calcula la distancia en metros desde la ubicación de un usuario (asociado a una cuenta) a una parada específica.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Distancia calculada exitosamente.",
                    content = @Content(mediaType = "application/json", schema = @Schema(example = "523.45"))),
            @ApiResponse(responseCode = "404", description = "Cuenta o parada no encontrada.")
    })
    @GetMapping("/{idCuenta}/distancia-parada/{idParada}")
    public ResponseEntity<Double> calcularDistanciaAParada(
            @Parameter(description = "ID de la cuenta del usuario.", required = true) @PathVariable Long idCuenta,
            @Parameter(description = "ID de la parada de colectivo.", required = true) @PathVariable Long idParada) {
        Double distancia = cuentaService.calcularDistanciaAParada(idCuenta, idParada);
        if (distancia == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(distancia);
    }


    @Operation(summary = "Recargar saldo a una cuenta", description = "Añade un monto específico al saldo de una cuenta.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Saldo recargado exitosamente.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Cuenta.class))),
            @ApiResponse(responseCode = "400", description = "Monto o ID inválido."),
            @ApiResponse(responseCode = "404", description = "Cuenta no encontrada."),
            @ApiResponse(responseCode = "409", description = "Conflicto de estado (ej. la cuenta está anulada).")
    })
    @PutMapping("/recargar/{id}/monto/{monto}")
    public ResponseEntity<?> recargarSaldo(
            @Parameter(description = "ID de la cuenta a recargar.", required = true) @PathVariable Long id,
            @Parameter(description = "Monto a recargar.", required = true) @PathVariable String monto) {
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

    @Operation(summary = "Obtener historial de viajes de una cuenta", description = "Devuelve una lista con todos los viajes realizados por el usuario asociado a la cuenta.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Historial de viajes obtenido exitosamente."),
            @ApiResponse(responseCode = "404", description = "Cuenta no encontrada o sin viajes registrados.")
    })
    @GetMapping("/viajes/{id}")
    public ResponseEntity<?> getViajesPorUsuarioYPeriodo(@Parameter(description = "ID de la cuenta.", required = true) @PathVariable Long id) {
        try {
            List<?> viajes = cuentaService.historialViajes(id);
            return ResponseEntity.ok(viajes);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

    @Operation(summary = "Obtener cuentas por tipo", description = "Devuelve una lista de cuentas que coinciden con el tipo especificado (BASICA o PREMIUM).")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de cuentas obtenida exitosamente."),
            @ApiResponse(responseCode = "404", description = "No se encontraron cuentas de ese tipo.")
    })
    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<?> obtenerPorTipo(@Parameter(description = "Tipo de cuenta a buscar (BASICA o PREMIUM).", required = true) @PathVariable String tipo) {
        try {
            List<?> cuentas = cuentaService.obtenerPorTipo(tipo.toUpperCase());
            return ResponseEntity.ok(cuentas);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

    @Operation(summary = "Obtener la parada más cercana a un usuario", description = "Calcula y devuelve la parada de colectivo más cercana a la ubicación actual del usuario.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Parada más cercana encontrada.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ParadaDto.class))),
            @ApiResponse(responseCode = "404", description = "Cuenta no encontrada o no se pudo determinar la ubicación.")
    })
    @GetMapping("/{idCuenta}/parada-cercana")
    public ResponseEntity<?> obtenerParadaCercana(@Parameter(description = "ID de la cuenta del usuario.", required = true) @PathVariable Long idCuenta) {
        try {
            ParadaDto paradaMasCercana = cuentaService.paradaMasCercana(idCuenta);
            return ResponseEntity.ok(paradaMasCercana);
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