package org.example.msvcfacturacion.clients;


import org.example.msvcfacturacion.config.FeignClientConfig;
import org.example.msvcfacturacion.models.Cuenta;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "msvc-cuenta", url = "http://localhost:8001",configuration = FeignClientConfig.class )
public interface CuentaClient {

    @GetMapping("/cuentas/{id}")
    Cuenta obtenerCuentaPorId(@PathVariable("id") Long id);

    @PutMapping("/cuentas/{id}")
    ResponseEntity<Cuenta> actualizarCuenta(@PathVariable("id") Long id, @RequestBody Cuenta cuenta);
}
