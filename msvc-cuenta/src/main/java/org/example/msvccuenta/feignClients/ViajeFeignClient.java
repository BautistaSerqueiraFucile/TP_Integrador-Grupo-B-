package org.example.msvccuenta.feignClients;

import org.example.msvccuenta.config.FeignClientConfig;
import org.example.msvccuenta.entities.dto.ViajeDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "msvc-viaje", url = "http://localhost:8003/viajes",configuration = FeignClientConfig.class)
public interface ViajeFeignClient {

    @GetMapping("/historial")
    List<ViajeDto> getViajesPorUsuarioYPeriodo(
            @RequestParam("idUsuario") Long idUsuario,
            @RequestParam(value = "fechaDesde", required = false) String fechaDesde,
            @RequestParam(value = "fechaHasta", required = false) String fechaHasta
    );
}