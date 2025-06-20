package org.example.msvcadmin.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "scooter", url = "http://localhost:8007")
public interface ScooterClient {

    @PutMapping("/scooters/{id}/estado")
    void cambiarEstado(@PathVariable("id") Long id, @RequestParam("estado") String estado);

    @PutMapping("/scooters/{id}/reset-km")
    void resetearKilometraje(@PathVariable("id") Long id);
}
