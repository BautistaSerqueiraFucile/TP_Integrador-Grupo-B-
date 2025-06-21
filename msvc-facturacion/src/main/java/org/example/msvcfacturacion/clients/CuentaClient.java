package org.example.msvcfacturacion.clients;


import org.example.msvcfacturacion.models.Cuenta;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "msvc-cuenta", url = "http://localhost:8001" )
public interface CuentaClient {

    @GetMapping("/cuentas/{id}")
    Cuenta obtenerCuentaPorId(@PathVariable("id") Long id);
}
