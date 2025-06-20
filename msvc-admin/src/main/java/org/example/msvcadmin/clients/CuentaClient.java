package org.example.msvcadmin.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient(name = "cuenta", url = "http://localhost:8001")
public interface CuentaClient {

    @PutMapping("/cuentas/{id}/bloquear")
    void bloquearCuenta(@PathVariable("id") Long id);

    @DeleteMapping("/cuentas/{id}")
    void anularCuenta(@PathVariable("id") Long id);
}
