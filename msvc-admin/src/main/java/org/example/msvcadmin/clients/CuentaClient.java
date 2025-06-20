package org.example.msvcadmin.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@FeignClient(name = "cuenta", url = "http://localhost:8001")
public interface CuentaClient {

    @PatchMapping("/cuentas/anular/{id}")
    void anularCuenta(@PathVariable("id") Long id);

    @PatchMapping("/cuentas/activar/{id}")
    void activarCuenta(@PathVariable("id") Long id);

    @DeleteMapping("/cuentas/{id}")
    void eliminarCuenta(@PathVariable("id") Long id);

    @PostMapping("/cuentas")
    void crearCuenta(@RequestBody Map<String, Object> datos);

    @PutMapping("/cuentas/{id}")
    void editarCuenta(@PathVariable("id") Long id, @RequestBody Map<String, Object> datos);
}
