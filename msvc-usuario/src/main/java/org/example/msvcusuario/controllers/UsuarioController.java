package org.example.msvcusuario.controllers;

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

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    private final UsuarioService usuarioService;
    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/")
    public List<Usuario> listar() {
        return usuarioService.listar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable String id) {
        try {
            Long usuarioId = Long.parseLong(id);
            Usuario usuario = usuarioService.buscarPorId(usuarioId);
            return ResponseEntity.ok(usuario);
        } catch (NumberFormatException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"El ID debe ser un número válido.\"}");
        } catch (UsuarioNoEncontradoException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

    @PostMapping("/") // Ruta corregida a la convención REST estándar
    public ResponseEntity<?> crear(@Valid @RequestBody Usuario usuario, BindingResult result) {
        if (result.hasErrors()) {
            return validar(result);
        }
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.crear(usuario));
        }

    catch (DataIntegrityViolationException e) {
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

        return ResponseEntity.badRequest().body("{\"error\":\"" + mensajeError + "\"}");
    }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@Valid @RequestBody Usuario usuario, BindingResult result, @PathVariable String id) {
        if (result.hasErrors()) {
            return validar(result);
        }
        try {
            Long usuarioId = Long.parseLong(id);
            Usuario usuarioActualizado = usuarioService.actualizar(usuarioId, usuario);
            return ResponseEntity.ok(usuarioActualizado);
        } catch (NumberFormatException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"El ID debe ser un número válido.\"}");
        } catch (UsuarioNoEncontradoException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"" + e.getMessage() + "\"}");
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.badRequest().body("{\"error\":\"Error al actualizar. El email '" + usuario.getEmail() + "' podría estar ya en uso.\"}");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable String id) {
        try {
            Long usuarioId = Long.parseLong(id);
            usuarioService.eliminar(usuarioId);
            return ResponseEntity.noContent().build(); // 204 No Content
        } catch (NumberFormatException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"El ID debe ser un número válido.\"}");
        } catch (UsuarioNoEncontradoException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

    @PatchMapping("/{id}/set-admin")
    public ResponseEntity<?> setAdmin(@PathVariable String id) { // Cambiado a String
        try {
            Long usuarioId = Long.parseLong(id);
            Usuario usuarioActualizado = usuarioService.actualizarRol(usuarioId, RolUsuario.ADMIN);
            return ResponseEntity.ok(usuarioActualizado);
        } catch (NumberFormatException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"El ID debe ser un número válido.\"}");
        } catch (UsuarioNoEncontradoException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

    @PatchMapping("/{id}/set-usuario")
    public ResponseEntity<?> setUsuario(@PathVariable String id) { // Cambiado a String
        try {
            Long usuarioId = Long.parseLong(id);
            Usuario usuarioActualizado = usuarioService.actualizarRol(usuarioId, RolUsuario.USUARIO);
            return ResponseEntity.ok(usuarioActualizado);
        } catch (NumberFormatException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"El ID debe ser un número válido.\"}");
        } catch (UsuarioNoEncontradoException e) {
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