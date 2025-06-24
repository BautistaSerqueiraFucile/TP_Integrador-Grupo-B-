package org.example.msvcreporte.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@FeignClient(name = "facturacion", url = "http://localhost:8005")
public interface FacturacionClient {

    @GetMapping("/facturacion/historial")
    List<Map<String, Object>> obtenerFacturas(
            @RequestParam("fechaDesde") String fechaDesde,
            @RequestParam("fechaHasta") String fechaHasta,
            @RequestParam(value = "idUsuario", required = false) Long idUsuario
    );
}
