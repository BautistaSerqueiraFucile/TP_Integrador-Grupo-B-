package org.example.msvcviaje.clients;

import org.example.msvcviaje.model.Monopatin;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "msvc-monopatin", url= "http://localhost:8007")
public interface MonopatinClient {

    @PutMapping("/monopatines/{id}/tiemposYKilometros")
    void tiemposYKilometros(
            @PathVariable("id") String id,
            @RequestParam("tiempoDeUso") double tiempoDeUso,
            @RequestParam("tiempoPausa") double tiempoPausa,
            @RequestParam("kilometros") double kilometros
    );

    @GetMapping("/monopatines/porParada")
    Monopatin getMonopatinPorParada(
            @RequestParam("parada") Long id_parada
    );

    @PutMapping("/monopatines/estado/{id}/{estado}")
    ResponseEntity<Monopatin> modificarEstado(
            @PathVariable("id") String id,
            @PathVariable("estado") String estado
    );

}
