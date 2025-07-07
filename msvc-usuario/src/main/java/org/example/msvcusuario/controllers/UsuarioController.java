package org.example.msvcusuario.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.example.msvcusuario.entities.Usuario;
import org.example.msvcusuario.exceptions.UsuarioNoEncontradoException;
import org.example.msvcusuario.services.UsuarioService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.example.msvcusuario.entities.RolUsuario;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controlador para gestionar las operaciones CRUD y de negocio sobre los Usuarios.
 * Expone los endpoints de la API REST para interactuar con la entidad Usuario.
 */
@Tag(name = "Usuarios", description = "Operaciones relacionadas con los usuarios")
@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    private final UsuarioService usuarioService;

    /**
     * Constructor para la inyección de dependencias del servicio de usuarios.
     *
     * @param usuarioService El servicio que contiene la lógica de negocio para los usuarios.
     */
    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    /**
     * Obtiene una lista de todos los usuarios existentes.
     *
     * @return Una lista de objetos Usuario.
     */
    @Operation(summary = "Obtener todos los usuarios")
    @ApiResponse(responseCode = "200", description = "Lista de usuarios obtenida exitosamente",
            content = @Content(schema = @Schema(implementation = Usuario.class)))
    @GetMapping("")
    public List<Usuario> listar() {
        return usuarioService.listar();
    }

    /**
     * Busca y devuelve un usuario por su ID.
     *
     * @param id El ID del usuario a buscar (como String para manejar errores de formato).
     * @return Un ResponseEntity con el Usuario si se encuentra (200 OK), o un error (400 o 404).
     */
    @Operation(summary = "Obtener un usuario por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario encontrado",
                    content = @Content(schema = @Schema(implementation = Usuario.class))),
            @ApiResponse(responseCode = "400", description = "ID inválido (no es un número)",
                    content = @Content(schema = @Schema(example = "{\"error\":\"El ID debe ser un número válido.\"}"))),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado",
                    content = @Content(schema = @Schema(example = "{\"error\":\"Usuario no encontrado con ID: 123\"}")))
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@Parameter(description = "ID del usuario a buscar", example = "1", required = true) @PathVariable String id) {
        try {
            Long usuarioId = Long.parseLong(id);
            Usuario usuario = usuarioService.buscarPorId(usuarioId);
            return ResponseEntity.ok(usuario);
        } catch (NumberFormatException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "El ID debe ser un número válido."));
        } catch (UsuarioNoEncontradoException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        }
    }

    /**
     * Crea un nuevo usuario con los datos proporcionados.
     *
     * @param usuario El objeto Usuario a crear, validado por anotaciones de Jakarta Validation.
     * @param result  El resultado de la validación del objeto Usuario.
     * @return Un ResponseEntity con el Usuario creado (201 Created) o un error (400 Bad Request).
     */
    @Operation(summary = "Crear un nuevo usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuario creado exitosamente",
                    content = @Content(schema = @Schema(implementation = Usuario.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos o duplicados",
                    content = @Content(schema = @Schema(example = "{\"error\":\"El email 'usuario@example.com' ya está en uso.\"}")))
    })
    @PostMapping("")
    public ResponseEntity<?> crear(@Valid @RequestBody Usuario usuario, BindingResult result) {
        if (result.hasErrors()) {
            return validar(result);
        }
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.crear(usuario));
        } catch (DataIntegrityViolationException e) {
            String rootMsg = e.getMostSpecificCause().getMessage();
            String mensajeError;

            if (rootMsg.contains("Duplicate entry") || rootMsg.contains("duplicate key")) {
                if (rootMsg.contains(usuario.getNumeroCelular())) {
                    mensajeError = "El número de celular '" + usuario.getNumeroCelular() + "' ya está registrado.";
                } else if (rootMsg.contains(usuario.getEmail())) {
                    mensajeError = "El email '" + usuario.getEmail() + "' ya está en uso.";
                } else {
                    mensajeError = "Uno de los campos únicos (como email o número de celular) ya existe en la base de datos.";
                }
            } else if (rootMsg.contains("cannot be null") || rootMsg.contains("violates not-null")) {
                mensajeError = "Falta un campo obligatorio o se ha enviado como nulo. Revisa que todos los campos requeridos estén presentes y con valor.";
            } else {
                mensajeError = "Ha ocurrido un error de integridad de datos. Por favor, revisa los datos enviados.";
            }
            return ResponseEntity.badRequest().body(Map.of("error", mensajeError));
        }
    }

    /**
     * Actualiza un usuario existente con los datos proporcionados.
     *
     * @param usuario El objeto Usuario con los nuevos datos, validado por anotaciones de Jakarta Validation.
     * @param result  El resultado de la validación del objeto Usuario.
     * @param id      El ID del usuario a actualizar (como String para manejar errores de formato).
     * @return Un ResponseEntity con el Usuario actualizado (200 OK) o un error (400 o 404).
     */
    @Operation(summary = "Actualizar un usuario existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario actualizado exitosamente",
                    content = @Content(schema = @Schema(implementation = Usuario.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos o ID inválido",
                    content = @Content(schema = @Schema(example = "{\"error\":\"El ID debe ser un número válido.\"}"))),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado",
                    content = @Content(schema = @Schema(example = "{\"error\":\"Usuario no encontrado con ID: 123\"}")))
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@Valid @RequestBody Usuario usuario, BindingResult result,
                                        @Parameter(description = "ID del usuario a actualizar", example = "1", required = true) @PathVariable String id) {
        if (result.hasErrors()) {
            return validar(result);
        }
        try {
            Long usuarioId = Long.parseLong(id);
            Usuario usuarioActualizado = usuarioService.actualizar(usuarioId, usuario);
            return ResponseEntity.ok(usuarioActualizado);
        } catch (NumberFormatException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "El ID debe ser un número válido."));
        } catch (UsuarioNoEncontradoException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.badRequest().body(Map.of("error", "Error al actualizar. El email '" + usuario.getEmail() + "' podría estar ya en uso."));
        }
    }

    /**
     * Elimina un usuario por su ID.
     *
     * @param id El ID del usuario a eliminar .
     * @return Un ResponseEntity con estado 204 No Content si se elimina exitosamente, o un error (400 o 404).
     */
    @Operation(summary = "Eliminar un usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Usuario eliminado exitosamente"),
            @ApiResponse(responseCode = "400", description = "ID inválido (no es un número)",
                    content = @Content(schema = @Schema(example = "{\"error\":\"El ID debe ser un número válido.\"}"))),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado",
                    content = @Content(schema = @Schema(example = "{\"error\":\"Usuario no encontrado con ID: 123\"}")))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@Parameter(description = "ID del usuario a eliminar", example = "1", required = true) @PathVariable String id) {
        try {
            Long usuarioId = Long.parseLong(id);
            usuarioService.eliminar(usuarioId);
            return ResponseEntity.noContent().build();
        } catch (NumberFormatException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "El ID debe ser un número válido."));
        } catch (UsuarioNoEncontradoException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        }
    }

    /**
     * Asigna el rol de administrador a un usuario.
     *
     * @param id El ID del usuario al que se le asignará el rol de administrador.
     * @return Un ResponseEntity con el Usuario actualizado (200 OK) o un error (400 o 404).
     */
    @Operation(summary = "Asignar rol de administrador a un usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Rol de administrador asignado exitosamente",
                    content = @Content(schema = @Schema(implementation = Usuario.class))),
            @ApiResponse(responseCode = "400", description = "ID inválido (no es un número)",
                    content = @Content(schema = @Schema(example = "{\"error\":\"El ID debe ser un número válido.\"}"))),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado",
                    content = @Content(schema = @Schema(example = "{\"error\":\"Usuario no encontrado con ID: 123\"}")))
    })
    @PutMapping("/{id}/set-admin")
    public ResponseEntity<?> setAdmin(@Parameter(description = "ID del usuario", example = "1", required = true) @PathVariable String id) {
        try {
            Long usuarioId = Long.parseLong(id);
            Usuario usuarioActualizado = usuarioService.actualizarRol(usuarioId, RolUsuario.ADMIN);
            return ResponseEntity.ok(usuarioActualizado);
        } catch (NumberFormatException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "El ID debe ser un número válido."));
        } catch (UsuarioNoEncontradoException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        }
    }

    /**
     * Asigna el rol de Usuario (normal) a un usuario existente.
     *
     * @param id El ID del usuario al que se le asignará el rol.
     * @return Un ResponseEntity con el usuario actualizado (200 OK) o un error.
     */
    @Operation(summary = "Asignar rol de usuario (normal) a un usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Rol de usuario asignado exitosamente",
                    content = @Content(schema = @Schema(implementation = Usuario.class))),
            @ApiResponse(responseCode = "400", description = "ID inválido (no es un número)",
                    content = @Content(schema = @Schema(example = "{\"error\":\"El ID debe ser un número válido.\"}"))),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado",
                    content = @Content(schema = @Schema(example = "{\"error\":\"Usuario no encontrado con ID: 123\"}")))
    })
    @PutMapping("/{id}/set-usuario")
    public ResponseEntity<?> setUsuario(@Parameter(description = "ID del usuario", example = "1", required = true) @PathVariable String id) {
        try {
            Long usuarioId = Long.parseLong(id);
            Usuario usuarioActualizado = usuarioService.actualizarRol(usuarioId, RolUsuario.USUARIO);
            return ResponseEntity.ok(usuarioActualizado);
        } catch (NumberFormatException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "El ID debe ser un número válido."));
        } catch (UsuarioNoEncontradoException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        }
    }

    /**
     * Métdo privado de utilidad para formatear los errores de validación.
     *
     * @param result El objeto BindingResult que contiene los errores.
     * @return Un ResponseEntity de tipo 400 Bad Request con un mapa de los errores.
     */
    private static ResponseEntity<Map<String, String>> validar(BindingResult result) {
        Map<String, String> errores = new HashMap<>();
        result.getFieldErrors().forEach(err -> {
            errores.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errores);
    }
}