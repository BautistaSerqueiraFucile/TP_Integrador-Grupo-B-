package org.example.msvcadmin.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@FeignClient(name = "parada", url = "http://localhost:8008")
public interface ParadaClient {

    @PostMapping("/parada")
    void crearParada(@RequestBody Map<String, Object> datos);

    @PutMapping("/parada/{id}")
    void editarParada(@PathVariable("id") Long id, @RequestBody Map<String, Object> datos);

    @DeleteMapping("/parada/{id}")
    void eliminarParada(@PathVariable("id") Long id);
}
