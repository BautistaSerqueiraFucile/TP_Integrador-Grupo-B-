package org.example.msvcadmin.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Cliente Feign que se comunica con el microservicio de monopatines (puerto 8007).
 * Define los endpoints que el microservicio de administración puede consumir.
 */
@FeignClient(name = "monopatin", url = "http://localhost:8007")
public interface ScooterClient {
    /**
     * Cambia el estado de un monopatín (por ejemplo: "activo", "en reparacion", etc.).
     */
    @PatchMapping("/monopatines/{id}/estado/{estado}")
    void cambiarEstado(@PathVariable("id") Long id, @PathVariable("estado") String estado);
    /**
     * Resetea el kilometraje de un monopatín (por mantenimiento, por ejemplo).
     */
    @PutMapping("/monopatin/{id}/reset-km")
    void resetearKilometraje(@PathVariable("id") Long id);
    /**
     * Agrega un nuevo monopatín al sistema.
     * Se espera que el cuerpo contenga los campos necesarios para crear un monopatin.
     */
    @PostMapping("/monopatines")
    void agregarMonopatin(@RequestBody Map<String, Object> datos);
    /**
     * Elimina un monopatín existente del sistema.
     */
    @DeleteMapping("/monopatines/{id}")
    void eliminarMonopatin(@PathVariable("id") Long id);
}
