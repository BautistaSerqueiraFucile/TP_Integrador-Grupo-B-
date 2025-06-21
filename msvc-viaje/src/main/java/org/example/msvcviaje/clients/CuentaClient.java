package org.example.msvcviaje.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.math.BigDecimal;

@FeignClient(name = "msvc-cuenta", url= "http://localhost:8001")
public interface CuentaClient {

    @GetMapping("/cuentas/saldo/{id}")
    BigDecimal obtenerSaldo(@PathVariable Long id);
}
