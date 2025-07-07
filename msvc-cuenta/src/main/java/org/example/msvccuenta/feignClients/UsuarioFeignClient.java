package org.example.msvccuenta.feignClients;

import org.example.msvccuenta.config.FeignClientConfig;
import org.springframework.web.bind.annotation.PathVariable;
import org.example.msvccuenta.entities.dto.UsuarioDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "msvc-usuario", url = "http://localhost:8002/usuarios",configuration = FeignClientConfig.class)
/**
 * Cliente Feign para comunicarse con el microservicio de Usuarios (msvc-usuario).
 * Se utiliza para obtener la ubicación de un usuario.
 */
@FeignClient(name = "msvc-usuario", url = "http://localhost:8002/usuarios")
public interface UsuarioFeignClient {
    /**
     * Obtiene los datos de un usuario por su ID.
     * @param id El ID del usuario.
     * @return Un {@link UsuarioDto}
     */
    @GetMapping("/{id}")
    UsuarioDto getUsuarioById(@PathVariable("id") Long id);
}
