package org.example.msvcadmin.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

/**
 * Cliente Feign que se comunica con el microservicio de facturación (puerto 8005).
 * Permite a la administración crear y modificar tarifas y tarifas adicionales del sistema.
 */
@FeignClient(name = "facturacion", url = "http://localhost:8005")
public interface FacturacionClient {

    /**
     * Crea una nueva tarifa estándar en el sistema de facturación.
     *
     * @param tarifa Mapa con los datos necesarios para crear la tarifa (por ejemplo, costo base, duración, etc.).
     */
    @PostMapping("/tarifas")
    void crearTarifa(@RequestBody Map<String, Object> tarifa);

    /**
     * Modifica una tarifa existente.
     *
     * @param id ID de la tarifa a modificar.
     * @param tarifa Mapa con los nuevos datos de la tarifa.
     */
    @PutMapping("/tarifas/{id}")
    void modificarTarifa(@PathVariable("id") Long id, @RequestBody Map<String, Object> tarifa);

    /**
     * Crea una nueva tarifa extra (por ejemplo, penalizaciones o cargos especiales).
     *
     * @param tarifaExtra Mapa con los datos de la tarifa extra.
     */
    @PostMapping("/tarifas-extra")
    void crearTarifaExtra(@RequestBody Map<String, Object> tarifaExtra);

    /**
     * Modifica una tarifa extra existente.
     *
     * @param id ID de la tarifa extra a modificar.
     * @param tarifaExtra Mapa con los nuevos datos de la tarifa extra.
     */
    @PutMapping("/tarifas-extra/{id}")
    void modificarTarifaExtra(@PathVariable("id") Long id, @RequestBody Map<String, Object> tarifaExtra);

}
