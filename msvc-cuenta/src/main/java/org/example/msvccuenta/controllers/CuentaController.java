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
import org.example.msvccuenta.entities.dto.ParadaConDistanciaDto;
import org.example.msvccuenta.entities.dto.ParadaDto;
import org.example.msvccuenta.entities.dto.ViajeDto;
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
    public ResponseEntity<Cuenta> buscarPorId(@Parameter(description = "ID de la cuenta a buscar.", required = true) @PathVariable Long id) {
        return ResponseEntity.ok(cuentaService.buscarPorId(id));
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
    public ResponseEntity<?> actualizar(@Valid @RequestBody Cuenta cuenta, BindingResult result, @Parameter(description = "ID de la cuenta a actualizar.", required = true) @PathVariable Long id) {
        if (result.hasErrors()) {
            return validar(result);
        }
        return ResponseEntity.ok(cuentaService.actualizar(id, cuenta));
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
    public ResponseEntity<Void> eliminar(@Parameter(description = "ID de la cuenta a eliminar.", required = true) @PathVariable Long id) {
        cuentaService.eliminar(id);
        return ResponseEntity.noContent().build();
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
    public ResponseEntity<Cuenta> anular(@Parameter(description = "ID de la cuenta a anular.", required = true) @PathVariable Long id) {
        return ResponseEntity.ok(cuentaService.anularCuenta(id));
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
    public ResponseEntity<Cuenta> activar(@Parameter(description = "ID de la cuenta a activar.", required = true) @PathVariable Long id) {
        return ResponseEntity.ok(cuentaService.activarCuenta(id));
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
    public ResponseEntity<Double> obtenerSaldo(@Parameter(description = "ID de la cuenta.", required = true) @PathVariable Long id) {
        return ResponseEntity.ok(cuentaService.obtenerSaldo(id));
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
    public ResponseEntity<Cuenta> setPlan(@Parameter(description = "ID de la cuenta.", required = true) @PathVariable Long id,
                                          @Parameter(description = "Tipo de cuenta a establecer (BASICA o PREMIUM).", required = true) @PathVariable TipoCuenta tipo) {
        return ResponseEntity.ok(cuentaService.actualizarTipoCuenta(id, tipo));
    }


    /**
     * Calcula la distancia en kilometros desde la ubicación de un usuario a una parada específica.
     * @param idCuenta El ID de la cuenta del usuario.
     * @param idParada El ID de la parada de monopatines.
     * @param idUsuario (Opcional) El ID del usuario específico de la cuenta. Si no se provee, se usa el primer usuario asociado.
     * @return Un {@link ResponseEntity} con la distancia calculada (Double) o un error si no se encuentra la cuenta o la parada.
     */
    @Operation(summary = "Calcular distancia a una parada en KM",
            description = "Calcula la distancia en KILÓMETROS desde la ubicación de un usuario a una parada específica. " +
                    "Se puede especificar un 'idUsuario' opcional de la cuenta.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Distancia calculada exitosamente.",
                    content = @Content(mediaType = "application/json", schema = @Schema(example = "0.523"))),
            @ApiResponse(responseCode = "400", description = "El usuario especificado no pertenece a la cuenta."),
            @ApiResponse(responseCode = "404", description = "Cuenta o parada no encontrada.")
    })
    @GetMapping("/{idCuenta}/distancia-parada/{idParada}")
    public ResponseEntity<Double> calcularDistanciaAParada(
            @Parameter(description = "ID de la cuenta del usuario.", required = true) @PathVariable Long idCuenta,
            @Parameter(description = "ID de la parada de monopatines.", required = true) @PathVariable Long idParada,
            @Parameter(description = "(Opcional) ID del usuario específico a usar para el cálculo.") @RequestParam(required = false) Long idUsuario) {
        return ResponseEntity.ok(cuentaService.calcularDistanciaAParada(idCuenta, idParada, idUsuario));
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
    public ResponseEntity<Cuenta> recargarSaldo(
            @Parameter(description = "ID de la cuenta a recargar.", required = true) @PathVariable Long id,
            @Parameter(description = "Monto a recargar.", required = true) @PathVariable Double monto) {
        return ResponseEntity.ok(cuentaService.recargarSaldo(id, monto));
    }


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
    @GetMapping("/viajes/{idCuenta}")
    public ResponseEntity<List<ViajeDto>> getViajesPorUsuarioYPeriodo(
            @Parameter(description = "ID de la cuenta.", required = true) @PathVariable Long idCuenta,
            @Parameter(description = "(Opcional) ID del usuario para filtrar el historial.") @RequestParam(required = false) Long idUsuario
    ) {
        return ResponseEntity.ok(cuentaService.getViajesPorUsuarioYPeriodo(idCuenta, idUsuario));
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
    public ResponseEntity<List<Cuenta>> obtenerPorTipo(@Parameter(description = "Tipo de cuenta a buscar (BASICA o PREMIUM).", required = true) @PathVariable String tipo) {
        return ResponseEntity.ok(cuentaService.obtenerPorTipo(tipo.toUpperCase()));
    }


    /**
     * Encuentra las paradas de monopatines y las devuelve ordenadas por cercanía a un usuario.
     * @param idCuenta El ID de la cuenta del usuario.
     * @param idUsuario (Opcional) El ID del usuario específico a usar. Si no se provee, se usa el primer usuario de la cuenta.
     * @return Un {@link ResponseEntity} con la lista de DTOs de paradas ordenadas (200 OK) o un error.
     */
    @Operation(summary = "Obtener un listado de paradas ordenadas por cercanía (con distancia)",
            description = "Devuelve una lista de todas las paradas, ordenadas por distancia ascendente desde la ubicación de un usuario. " +
                    "Cada parada incluye la distancia calculada. Se puede especificar un 'idUsuario' opcional de la cuenta.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado de paradas cercanas obtenido.",
                    content = @Content(mediaType = "application/json",
                            // ¡CAMBIO CLAVE! Actualiza el schema para reflejar el nuevo DTO.
                            array = @ArraySchema(schema = @Schema(implementation = ParadaConDistanciaDto.class)))),
            @ApiResponse(responseCode = "400", description = "El usuario especificado no pertenece a la cuenta."),
            @ApiResponse(responseCode = "404", description = "Cuenta no encontrada o no se pudo determinar la ubicación.")
    })
    @GetMapping("/{idCuenta}/paradas-cercanas")
    public ResponseEntity<List<ParadaConDistanciaDto>> obtenerParadasCercanas(@Parameter(description = "ID de la cuenta del usuario.", required = true) @PathVariable Long idCuenta,
                                                                              @Parameter(description = "(Opcional) ID del usuario específico a usar para el cálculo.") @RequestParam(required = false) Long idUsuario) {
        return ResponseEntity.ok(cuentaService.paradasCercanas(idCuenta, idUsuario));    }

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