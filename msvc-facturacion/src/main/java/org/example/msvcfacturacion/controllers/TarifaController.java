package org.example.msvcfacturacion.controllers;

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

    @PatchMapping("tarifa/{tipo}/{valor}")
    public ResponseEntity<?> updateBasica(@PathVariable("tipo") String tipo ,@PathVariable("valor") double valor) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(tarifaService.updateTarifa(tipo,valor));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Error. Por favor intente más tarde.\"}");
        }
    }
}
