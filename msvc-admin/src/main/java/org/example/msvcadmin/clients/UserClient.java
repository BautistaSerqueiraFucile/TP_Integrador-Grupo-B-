package org.example.msvcadmin.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.Map;

/**
 * Cliente Feign que se comunica con el microservicio de usuarios (puerto 8002).
 * Permite consultar datos de usuarios y modificar su rol (admin o usuario común).
 */
@FeignClient(name = "user", url = "http://localhost:8002")
public interface UserClient {

    /**
     * Obtiene los datos de un usuario por su ID.
     *
     * @param id ID del usuario a consultar.
     * @return Un mapa con la información del usuario (por ejemplo: nombre, email, rol, etc.).
     */
    @GetMapping("/usuarios/{id}")
    Map<String, Object> obtenerUsuario(@PathVariable("id") Long id);

    /**
     * Asigna el rol de administrador a un usuario.
     *
     * @param id ID del usuario a modificar.
     */
    @PutMapping("/usuarios/{id}/set-admin")
    void setearComoAdmin(@PathVariable("id") Long id);

    /**
     * Quita el rol de administrador y vuelve a asignar el rol de usuario común.
     *
     * @param id ID del usuario a modificar.
     */
    @PutMapping("/usuarios/{id}/set-usuario")
    void quitarRolAdmin(@PathVariable("id") Long id);
}
