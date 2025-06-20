package org.example.msvcreporte.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "facturacion", url = "http://localhost:8005")
public interface FacturacionClient {

    @GetMapping("/facturas")
    Double obtenerTotalFacturado(
            @RequestParam("fechaDesde") String fechaDesde,
            @RequestParam("fechaHasta") String fechaHasta
    );
}
