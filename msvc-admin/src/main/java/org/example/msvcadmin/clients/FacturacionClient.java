package org.example.msvcadmin.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@FeignClient(name = "facturacion", url = "http://localhost:8005")
public interface FacturacionClient {

    @PostMapping("/tarifas")
    void crearTarifa(@RequestBody Map<String, Object> tarifa);

    @PutMapping("/tarifas/{id}")
    void modificarTarifa(@PathVariable("id") Long id, @RequestBody Map<String, Object> tarifa);
}
