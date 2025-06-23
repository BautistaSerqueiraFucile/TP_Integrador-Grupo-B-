package org.example.msvcreporte.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "cuenta", url = "http://localhost:8001")
public interface CuentaClient {
    @GetMapping("/cuentas/{id}/usuarios")
    List<Long> obtenerUsuariosDeCuenta(@PathVariable("id") Long idCuenta);
}
