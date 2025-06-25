package org.example.msvcadmin.clients;

import org.example.msvcadmin.models.Tarifa;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Cliente Feign que se comunica con el microservicio de facturación (puerto 8005).
 * Permite a la administración crear y modificar tarifas y tarifas adicionales del sistema.
 */
@FeignClient(name = "msvc-facturacion", url = "http://localhost:8005")
public interface FacturacionClient {


    @PutMapping("facturacion/tarifa/{tipo}/{valor}")
    ResponseEntity<Tarifa> modificarTarifa(@PathVariable("tipo") String tipo, @PathVariable("valor") double valor);

}
