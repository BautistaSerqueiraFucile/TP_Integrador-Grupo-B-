package org.example.msvcviaje.clients;

import org.example.msvcviaje.dtos.FacturaRequestDTO;
import org.example.msvcviaje.dtos.TiemposViajeDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "msvc-facturacion", url ="http://localhost:8005" )
public interface FacturacionClient {

    @PostMapping("/facturacion")
    ResponseEntity<?> postFactura(@RequestBody FacturaRequestDTO datosFacturacion);
}
