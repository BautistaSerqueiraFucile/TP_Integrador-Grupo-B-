package org.example.msvcadmin.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@FeignClient(name = "parada", url = "http://localhost:8002")
public interface ParadaClient {

    @PostMapping("/paradas")
    void crearParada(@RequestBody Map<String, Object> datos);

    @PutMapping("/paradas/{id}")
    void editarParada(@PathVariable("id") Long id, @RequestBody Map<String, Object> datos);

    @DeleteMapping("/paradas/{id}")
    void eliminarParada(@PathVariable("id") Long id);
}
