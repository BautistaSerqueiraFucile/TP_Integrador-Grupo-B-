package org.example.msvccuenta.feignClients;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "msvc-usuario", url = "http://localhost:8002/usuarios")
public interface UsuarioFeignClient {
}
