package org.example.msvcadmin.clients;

import org.example.msvcadmin.models.Parada;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Cliente Feign que se comunica con el microservicio de paradas (puerto 8008).
 * Permite crear, editar y eliminar paradas desde el microservicio de administración.
 */
@FeignClient(name = "parada", url = "http://localhost:8008")
public interface ParadaClient {

    /**
     * Crea una nueva parada en el sistema.
     *
     * @param datos Mapa con los datos necesarios para la creación (nombre, ubicación, etc.).
     */
    @PostMapping("/paradas")
    void crearParada(@RequestBody Parada datos);

    /**
     * Edita una parada existente.
     *
     * @param id ID de la parada a editar.
     * @param datos Mapa con los nuevos datos de la parada.
     */
    @PutMapping("/paradas/{id}")
    void editarParada(@PathVariable("id") Long id, @RequestBody Parada datos);

    /**
     * Elimina una parada del sistema.
     *
     * @param id ID de la parada a eliminar.
     */
    @DeleteMapping("/paradas/{id}")
    void eliminarParada(@PathVariable("id") Long id);
}
