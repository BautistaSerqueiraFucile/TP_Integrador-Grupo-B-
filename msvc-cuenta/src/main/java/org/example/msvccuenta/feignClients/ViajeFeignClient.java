package org.example.msvccuenta.feignClients;

import org.example.msvccuenta.entities.dto.ViajeDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Cliente Feign para comunicarse con el microservicio de Viajes (msvc-viaje).
 * Permite obtener el historial de viajes de un usuario.
 */
@FeignClient(name = "msvc-viaje", url = "http://localhost:8003/viajes")
public interface ViajeFeignClient {
    /**
     * Obtiene el historial de viajes para un usuario específico.
     * @param idUsuario El ID del usuario cuyo historial se quiere consultar.
     * @return Una lista de {@link ViajeDto} representando los viajes.
     */
    @GetMapping("/historial")
    List<ViajeDto> getViajesPorUsuarioYPeriodo(
            @RequestParam Long idUsuario
    );
}
