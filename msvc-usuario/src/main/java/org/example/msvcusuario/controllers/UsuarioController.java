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

@Tag(name = "Usuarios", description = "Operaciones relacionadas con los usuarios")
@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @Operation(summary = "Obtener todos los usuarios")
    @ApiResponse(responseCode = "200", description = "Lista de usuarios obtenida exitosamente",
            content = @Content(schema = @Schema(implementation = Usuario.class)))
    @GetMapping("")
    public List<Usuario> listar() {
        return usuarioService.listar();
    }

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

    private static ResponseEntity<Map<String, String>> validar(BindingResult result) {
        Map<String, String> errores = new HashMap<>();
        result.getFieldErrors().forEach(err -> {
            errores.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errores);
    }
}