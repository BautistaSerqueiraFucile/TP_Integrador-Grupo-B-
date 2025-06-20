package org.example.msvcreporte.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@FeignClient(name = "viaje", url = "http://localhost:8003")
public interface ViajeClient {

    @GetMapping("/viajes/historial")
    List<Map<String, Object>> obtenerHistorialPorUsuarioYPeriodo(
            @RequestParam("idUsuario") Long usuarioId,
            @RequestParam(value = "fechaDesde", required = false) String fechaDesde,
            @RequestParam(value = "fechaHasta", required = false) String fechaHasta
    );

    @GetMapping("/viajes/por-monopatin-anio")
    List<Map<String, Object>> obtenerViajesPorMonopatinYAño(
            @RequestParam("monopatinId") Long monopatinId,
            @RequestParam("anio") int anio
    );

    @GetMapping("/viajes/por-monopatin")
    List<Map<String, Object>> obtenerViajesPorMonopatin(
            @RequestParam("monopatinId") Long monopatinId
    );

}
