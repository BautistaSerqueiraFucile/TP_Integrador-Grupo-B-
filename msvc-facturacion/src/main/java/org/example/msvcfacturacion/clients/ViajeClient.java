package org.example.msvcfacturacion.clients;

import org.example.msvcfacturacion.models.TiemposViaje;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "msvc-viaje", url="http://localhost:8003")
public interface ViajeClient {

    @GetMapping("viajes/tiempos/{id}")
    TiemposViaje getTiemposViaje(@PathVariable("id") Long id);

}
