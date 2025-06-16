package org.example.msvccuenta.feignClients;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "msvc-viaje", url = "http://localhost:8004/viajes")
public interface ViajeFeignClient {
}
