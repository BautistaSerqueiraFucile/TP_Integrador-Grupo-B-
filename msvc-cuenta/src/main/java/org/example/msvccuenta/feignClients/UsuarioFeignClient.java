package org.example.msvccuenta.feignClients;

import org.example.msvccuenta.entities.dto.UsuarioDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "msvc-usuario", url = "http://localhost:8002/usuarios")
public interface UsuarioFeignClient {
    @GetMapping("/{id}")
    UsuarioDto getUsuarioById(Long id);
}
