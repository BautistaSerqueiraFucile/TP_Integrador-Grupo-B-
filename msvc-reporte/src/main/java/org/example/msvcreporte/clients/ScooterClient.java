package org.example.msvcreporte.clients;

import org.example.msvcreporte.config.FeignClientConfig;
import org.example.msvcreporte.dto.ReporteUsoMonopatinDTO;
import org.example.msvcreporte.models.Monopatin;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Map;

@FeignClient(name = "msvc-monopatin", url = "http://localhost:8007",configuration = FeignClientConfig.class)
public interface ScooterClient {
    @GetMapping("/monopatines")
    List<Monopatin> obtenerTodosLosScooters();

    @GetMapping("/monopatines/{id}")
    Monopatin obtenerScooterPorId(@PathVariable("id") String id);

    @GetMapping("/monopatines/estadisticas")
    List<ReporteUsoMonopatinDTO> obtenerEstadisticas();
}
/*
* mono guarda km
* tiempo en total, sin pausa
 */