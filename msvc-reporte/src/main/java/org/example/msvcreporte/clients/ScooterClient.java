package org.example.msvcreporte.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Map;

@FeignClient(name = "msv-monopatin", url = "http://localhost:8007")
public interface ScooterClient {
    @GetMapping("/monopatines")
    List<Map<String, Object>> obtenerTodosLosScooters();

    @GetMapping("/monopatines/{id}")
    Map<String, Object> obtenerScooterPorId(@PathVariable("id") Long id);
}
/*
* mono guarda km
* tiempo en total, sin pausa
 */