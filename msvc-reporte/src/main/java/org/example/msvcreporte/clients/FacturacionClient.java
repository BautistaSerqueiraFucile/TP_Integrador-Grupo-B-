package org.example.msvcreporte.clients;

import org.example.msvcreporte.models.Factura;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

@FeignClient(name = "msvc-facturacion", url = "http://localhost:8005")
public interface FacturacionClient {

    @GetMapping("/facturacion/historial")
    ResponseEntity<List<Factura>> obtenerTotalFacturado(
            @RequestParam(value = "idUsuario",required = false) long idUsuario,
            @RequestParam("fechaDesde") LocalDate fechaDesde,
            @RequestParam("fechaHasta") LocalDate fechaHasta

    );
}
