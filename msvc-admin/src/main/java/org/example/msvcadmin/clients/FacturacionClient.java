package org.example.msvcadmin.clients;

import org.example.msvcadmin.config.FeignClientConfig;
import org.example.msvcadmin.models.Factura;
import org.example.msvcadmin.models.Tarifa;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * Cliente Feign que se comunica con el microservicio de facturación (puerto 8005).
 * Permite a la administración crear y modificar tarifas y tarifas adicionales del sistema.
 */
@FeignClient(name = "msvc-facturacion", url = "http://localhost:8005",configuration = FeignClientConfig.class)
public interface FacturacionClient {


    @PutMapping("facturacion/tarifa")
    ResponseEntity<Tarifa> modificarTarifa(@RequestBody Tarifa tarifa);


}
