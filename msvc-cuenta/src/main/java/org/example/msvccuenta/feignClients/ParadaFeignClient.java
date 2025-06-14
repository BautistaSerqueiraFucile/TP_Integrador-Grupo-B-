package org.example.msvccuenta.feignClients;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "msvc-parada", url = "http://localhost:8004/paradas")
public interface ParadaFeignClient {
}
