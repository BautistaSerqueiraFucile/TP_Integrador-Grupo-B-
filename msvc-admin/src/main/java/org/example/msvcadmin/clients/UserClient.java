package org.example.msvcadmin.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

@FeignClient(name = "user", url = "http://localhost:8006")
public interface UserClient {
    @GetMapping("/usuarios/{id}")
    Map<String, Object> obtenerUsuario(@PathVariable("id") Long id);
}
