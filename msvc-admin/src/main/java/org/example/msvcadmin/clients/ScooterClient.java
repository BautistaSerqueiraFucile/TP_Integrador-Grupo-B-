package org.example.msvcadmin.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@FeignClient(name = "monopatin", url = "http://localhost:8007")
public interface ScooterClient {

    @PatchMapping("/monopatines/{id}/estado")
    void cambiarEstado(@PathVariable("id") Long id, @RequestBody Map<String, Object> body);

    @PutMapping("/monopatin/{id}/reset-km")
    void resetearKilometraje(@PathVariable("id") Long id);

    @PostMapping("/monopatines")
    void agregarMonopatin(@RequestBody Map<String, Object> datos);

    @DeleteMapping("/monopatines/{id}")
    void eliminarMonopatin(@PathVariable("id") Long id);
}
/** monopatin
 * {estado}
 * agregar mono (exel)
 * quitar moto
 * @PutMapping("/scooters/{id}/estado") estado parametro {}
 */