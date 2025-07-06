package org.example.msvccuenta.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.example.msvccuenta.entities.Cuenta;
import org.example.msvccuenta.entities.TipoCuenta;
import org.example.msvccuenta.entities.dto.ParadaDto;
import org.example.msvccuenta.entities.dto.ViajeDto;
import org.example.msvccuenta.exceptions.CuentaNoEncontradaException;
import org.example.msvccuenta.services.CuentaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controlador para gestionar las operaciones relacionadas con las Cuentas.
 * Expone endpoints REST para crear, leer, actualizar y eliminar cuentas,
 * así como otras operaciones de negocio.
 */
@Tag(name = "Cuentas", description = "API para la gestión de cuentas de usuarios")
@RestController
@RequestMapping("/cuentas")
public class CuentaController {
    private final CuentaService cuentaService;

    /**
     * Constructor para inyección de dependencias del servicio de cuentas.
     * @param cuentaService El servicio que maneja la lógica de negocio de las cuentas.
     */
    public CuentaController(CuentaService cuentaService) {
        this.cuentaService = cuentaService;
    }

    /**
     * Obtiene una lista de todas las cuentas registradas.
     * @return Una lista de objetos {@link Cuenta}.
     */
    @Operation(summary = "Listar todas las cuentas", description = "Devuelve una lista con todas las cuentas existentes.")
    @ApiResponse(responseCode = "200", description = "Lista de cuentas obtenida exitosamente.")
    @GetMapping("")
    public List<Cuenta> listar() {
        return cuentaService.listar();
    }

    /**
     * Busca una cuenta por su identificador único.
     * @param id El ID de la cuenta a buscar.
     * @return Un {@link ResponseEntity} con la cuenta si se encuentra (200 OK),
     *         o un mensaje de error si el ID es inválido (400 Bad Request)
     *         o si la cuenta no existe (404 Not Found).
     */
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

    /**
     * Crea una nueva cuenta en el sistema.
     * @param cuenta El objeto {@link Cuenta} a crear, validado.
     * @param result El resultado de la validación de los datos de la cuenta.
     * @return Un {@link ResponseEntity} con la cuenta creada (201 Created) o un mapa de errores de validación (400 Bad Request).
     */
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

    /**
     * Actualiza los datos de una cuenta existente.
     * @param cuenta Los nuevos datos de la cuenta.
     * @param result El resultado de la validación de los datos.
     * @param id El ID de la cuenta a actualizar.
     * @return Un {@link ResponseEntity} con la cuenta actualizada (200 OK) o un mensaje de error (400 o 404).
     */
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

    /**
     * Elimina una cuenta del sistema por su ID.
     * @param id El ID de la cuenta a eliminar.
     * @return Un {@link ResponseEntity} sin contenido (204 No Content) si la eliminación fue exitosa, o un error (400 o 404).
     */
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


    /**
     * Cambia el estado de una cuenta a 'ANULADA'.
     * @param id El ID de la cuenta a anular.
     * @return Un {@link ResponseEntity} con la cuenta actualizada (200 OK) o un error (400 o 404).
     */
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

    /**
     * Cambia el estado de una cuenta a 'ACTIVA'.
     * @param id El ID de la cuenta a activar.
     * @return Un {@link ResponseEntity} con la cuenta actualizada (200 OK) o un error (400 o 404).
     */
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

    /**
     * Obtiene el saldo actual de una cuenta específica.
     * @param id El ID de la cuenta.
     * @return Un {@link ResponseEntity} con el saldo (Double) de la cuenta (200 OK) o un error (400 o 404).
     */
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

    /**
     * Establece el tipo de plan de una cuenta (BASICA o PREMIUM).
     * @param id El ID de la cuenta a modificar.
     * @param tipo El nuevo {@link TipoCuenta} a establecer.
     * @return Un {@link ResponseEntity} con la cuenta actualizada (200 OK) o un error (400 o 404).
     */
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

    /**
     * Calcula la distancia en metros desde la ubicación de un usuario a una parada específica.
     * @param idCuenta El ID de la cuenta del usuario.
     * @param idParada El ID de la parada de colectivo.
     * @return Un {@link ResponseEntity} con la distancia calculada (Double) o un 404 si no se encuentra la cuenta o la parada.
     */
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


    /**
     * Recarga saldo a una cuenta específica.
     * @param id El ID de la cuenta a recargar.
     * @param monto El monto a añadir al saldo.
     * @return Un {@link ResponseEntity} con la cuenta actualizada (200 OK) o un error (400, 404, o 409).
     */
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

    // En: C:/.../msvc-cuenta/src/main/java/org/example/msvccuenta/controllers/CuentaController.java

    /**
     * Obtiene el historial de viajes de una cuenta.
     * Puede filtrarse opcionalmente por un usuario específico de esa cuenta.
     * @param idCuenta El ID de la cuenta.
     * @param idUsuario (Opcional) El ID del usuario para filtrar el historial.
     * @return Un {@link ResponseEntity} con la lista de viajes (200 OK) o un error (400, 404).
     */
    @Operation(
            summary = "Obtener historial de viajes de una cuenta",
            description = "Devuelve una lista con los viajes de una cuenta. Si se proporciona el parámetro 'idUsuario', " +
                    "se filtra el historial para ese usuario específico (siempre que pertenezca a la cuenta)."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Historial de viajes obtenido exitosamente.",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = ViajeDto.class)))),
            @ApiResponse(responseCode = "400", description = "El usuario especificado no pertenece a esta cuenta."),
            @ApiResponse(responseCode = "404", description = "Cuenta no encontrada.")
    })
    @GetMapping("/viajes/{idCuenta}") // Renombramos {id} a {idCuenta} para más claridad
    public ResponseEntity<?> getViajesPorUsuarioYPeriodo(
            @Parameter(description = "ID de la cuenta.", required = true) @PathVariable Long idCuenta,
            @Parameter(description = "(Opcional) ID del usuario para filtrar el historial.") @RequestParam(required = false) Long idUsuario
    ) {
        try {
            // Ahora la llamada al servicio coincide con su firma (dos parámetros)
            List<ViajeDto> viajes = cuentaService.getViajesPorUsuarioYPeriodo(idCuenta, idUsuario);
            return ResponseEntity.ok(viajes);
        } catch (CuentaNoEncontradaException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"" + e.getMessage() + "\"}");
        } catch (IllegalArgumentException e) {
            // Este catch ahora también maneja el error si el usuario no pertenece a la cuenta
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"" + e.getMessage() + "\"}");
        } catch (Exception e) {
            // Un catch genérico por si algo más falla en la llamada Feign
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\":\"Ocurrió un error al comunicarse con el servicio de viajes: " + e.getMessage() + "\"}");
        }
    }

    /**
     * Obtiene una lista de cuentas filtradas por su tipo (BASICA o PREMIUM).
     * @param tipo El tipo de cuenta a buscar.
     * @return Un {@link ResponseEntity} con la lista de cuentas (200 OK) o un error (404).
     */
    @Operation(summary = "Obtener cuentas por tipo", description = "Devuelve una lista de cuentas que coinciden con el tipo especificado (BASICA o PREMIUM).")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de cuentas obtenida exitosamente.",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Cuenta.class)))),
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

    /**
     * Encuentra la parada de colectivo más cercana a la ubicación de un usuario.
     * @param idCuenta El ID de la cuenta del usuario.
     * @return Un {@link ResponseEntity} con el DTO de la parada más cercana (200 OK) o un error (404).
     */
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

    /**
     * Métdo de utilidad para procesar y empaquetar errores de validación.
     * @param result El objeto {@link BindingResult} que contiene los errores.
     * @return Un {@link ResponseEntity} con un mapa de errores y estado 400 Bad Request.
     */
    private static ResponseEntity<Map<String, String>> validar(BindingResult result) {
        Map<String, String> errores = new HashMap<>();
        result.getFieldErrors().forEach(err -> {
            errores.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errores);
    }
}