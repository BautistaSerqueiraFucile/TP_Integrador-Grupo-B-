package org.example.msvcfacturacion.controllers;

import org.example.msvcfacturacion.dtos.TarifaRequestDTO;
import org.example.msvcfacturacion.entities.Tarifa;
import org.example.msvcfacturacion.services.TarifaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/facturacion")
public class TarifaController {

    @Autowired
    private TarifaService tarifaService;

    @GetMapping("tarifas")
    public ResponseEntity<?> getTarifas() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(tarifaService.findAll());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Error. Por favor intente más tarde.\"}");
        }
    }

    @PutMapping("tarifa")
    public ResponseEntity<?> updateBasica(@RequestBody TarifaRequestDTO tarifaDTO) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(tarifaService.update(new Tarifa(tarifaDTO)));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Error. Por favor intente más tarde.\"}");
        }
    }
}
