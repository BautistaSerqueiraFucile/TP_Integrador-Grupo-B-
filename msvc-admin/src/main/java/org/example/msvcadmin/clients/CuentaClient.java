package org.example.msvcadmin.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Cliente Feign que se comunica con el microservicio de cuentas (puerto 8001).
 * Define los endpoints que el microservicio de administración puede consumir para gestionar cuentas de usuario.
 */
@FeignClient(name = "cuenta", url = "http://localhost:8001")
public interface CuentaClient {

    /**
     * Anula una cuenta existente.
     * Generalmente se usa para bloquear o deshabilitar la cuenta de un usuario.
     *
     * @param id ID de la cuenta a anular.
     */
    @PatchMapping("/cuentas/anular/{id}")
    void anularCuenta(@PathVariable("id") Long id);

    /**
     * Reactiva una cuenta previamente anulada o desactivada.
     *
     * @param id ID de la cuenta a reactivar.
     */
    @PatchMapping("/cuentas/activar/{id}")
    void activarCuenta(@PathVariable("id") Long id);

    /**
     * Elimina una cuenta definitivamente del sistema.
     *
     * @param id ID de la cuenta a eliminar.
     */
    @DeleteMapping("/cuentas/{id}")
    void eliminarCuenta(@PathVariable("id") Long id);

    /**
     * Crea una nueva cuenta en el sistema.
     * El cuerpo debe contener los campos necesarios para registrar una cuenta (por ejemplo: usuario, email, etc.).
     *
     * @param datos Mapa con los datos de la cuenta a crear.
     */
    @PostMapping("/cuentas")
    void crearCuenta(@RequestBody Map<String, Object> datos);

    /**
     * Edita una cuenta existente.
     * Se puede utilizar para actualizar información como nombre, email, etc.
     *
     * @param id ID de la cuenta a editar.
     * @param datos Mapa con los nuevos datos de la cuenta.
     */
    @PutMapping("/cuentas/{id}")
    void editarCuenta(@PathVariable("id") Long id, @RequestBody Map<String, Object> datos);
}
