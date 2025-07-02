package org.example.msvcreporte.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Map;

@FeignClient(name = "user", url = "http://localhost:8002")
public interface UserClient {

    @GetMapping("/usuarios/{id}")
    Map<String, Object> obtenerUsuario(@PathVariable("id") Long idUsuario);

    @GetMapping("/usuarios")
    List<Map<String, Object>> obtenerTodosLosUsuarios(@PathVariable("tipo") String tipo);
}
