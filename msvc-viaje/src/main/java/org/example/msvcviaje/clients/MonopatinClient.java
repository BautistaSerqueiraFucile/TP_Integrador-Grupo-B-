package org.example.msvcviaje.clients;

import org.example.msvcviaje.model.Monopatin;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "msvc-monopatin", url= "http://localhost:8007")
public interface MonopatinClient {

    @PatchMapping("/monopatines/{id}/tiemposYKilometros")
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

    @PatchMapping("/monopatines/{id}/{estado}")
    Monopatin modificarEstado(
            @PathVariable("id") String id,
            @PathVariable("estado") String estado
    );

}
