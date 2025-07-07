package org.example.msvccuenta.feignClients;

import org.example.msvccuenta.entities.dto.ParadaDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
/**
 * Cliente Feign para comunicarse con el microservicio de Paradas (msvc-parada).
 * Permite obtener información sobre las paradas de monopatines.
 */
@FeignClient(name = "msvc-parada", url = "http://localhost:8008/paradas")
public interface ParadaFeignClient {

    /**
     * Obtiene los datos de una parada específica por su ID.
     * @param id El ID de la parada a buscar.
     * @return Un {@link ParadaDto} con la información de la parada.
     */
    @GetMapping("/{id}")
    ParadaDto getParadaById(@PathVariable("id") Long id);

    /**
     * Obtiene una lista de todas las paradas disponibles en el sistema.
     * @return Una lista de {@link ParadaDto}.
     */
    @GetMapping("")
    List<ParadaDto> listarParadas();
}
