package org.example.msvccuenta.feignClients;

import org.example.msvccuenta.config.FeignClientConfig;
import org.example.msvccuenta.entities.dto.ParadaDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "msvc-parada", url = "http://localhost:8008/paradas",configuration = FeignClientConfig.class)
public interface ParadaFeignClient {
    @GetMapping("/{id}")
    ParadaDto getParadaById(@PathVariable("id") Long id);

    @GetMapping("")
    List<ParadaDto> listarParadas();
}
