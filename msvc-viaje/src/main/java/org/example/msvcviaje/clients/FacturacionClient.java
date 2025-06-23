package org.example.msvcviaje.clients;

import org.example.msvcviaje.model.FacturaRequestModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "msvc-facturacion", url ="http://localhost:8005" )
public interface FacturacionClient {

    @PostMapping("/facturacion")
    ResponseEntity<?> postFactura(@RequestBody FacturaRequestModel datosFacturacion);
}
