package org.example.msvcviaje.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "msvc-parada", url= "http://localhost:8008")
public interface ParadaClient {

    @GetMapping("/paradas/distancia")
    double getKilometros(
            @RequestParam("parada1Id") Long parada1Id,
            @RequestParam("parada2Id") Long parada2Id
    );
}
