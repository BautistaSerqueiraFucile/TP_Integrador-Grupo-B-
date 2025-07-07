package org.example.msvcreporte.clients;

import org.example.msvcreporte.config.FeignClientConfig;
import org.example.msvcreporte.models.MonopatinViajeDTO;
import org.example.msvcreporte.models.Viaje;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@FeignClient(name = "msvc-viaje", url = "http://localhost:8003",configuration = FeignClientConfig.class)
public interface ViajeClient {

    @GetMapping("/viajes/historial")
    List<Viaje> obtenerHistorialPorUsuarioYPeriodo(
            @RequestParam(value = "idUsuario", required = false) Long usuarioId,
            @RequestParam(value = "fechaDesde", required = false) String fechaDesde,
            @RequestParam(value = "fechaHasta", required = false) String fechaHasta
    );
//esto
    @GetMapping("/viajes/por-monopatin-anio")
    List<Map<String, Object>> obtenerViajesPorMonopatinYAno(
            @RequestParam("monopatinId") String monopatinId,
            @RequestParam("anio") int anio
    );

    @GetMapping("/viajes/monopatines")
    List<MonopatinViajeDTO> obtenerMinViajesPorMonopatin(
            @RequestParam("minViaje") Long minViaje,
            @RequestParam("anio") Integer anio
    );

}
/*
 * junto los de mono
 * pausa viaje
 *
 */