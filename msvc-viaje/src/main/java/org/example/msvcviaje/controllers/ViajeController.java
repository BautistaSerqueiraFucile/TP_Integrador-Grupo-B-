package org.example.msvcviaje.controllers;

import jakarta.validation.Valid;
import org.example.msvcviaje.dtos.FinalizarViajeDTO;
import org.example.msvcviaje.dtos.ViajeRequestDTO;
import org.example.msvcviaje.entities.Viaje;
import org.example.msvcviaje.services.ViajeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/viajes")
public class ViajeController {

    @Autowired
    private ViajeService viajeService;

    @GetMapping("")
    public ResponseEntity<?> getAll() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(viajeService.findAll());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Error. Por favor intente más tarde.\"}");
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(viajeService.findById(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Error. Por favor intente más tarde.\"}");
        }
    }

    @PostMapping("")
    public ResponseEntity<?> save(@Valid @RequestBody ViajeRequestDTO entity){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(viajeService.save(new Viaje(entity)));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error. No se pudo ingresar, revise los campos e intente nuevamente.\"}");
        }
    }

    @PatchMapping("/{id}/finalizar")
    public ResponseEntity<?> finalizarViaje(@PathVariable Long id, @RequestBody FinalizarViajeDTO dto) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(viajeService.finalizarViaje(id, dto));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

    @PatchMapping("/{id}/pausar")
    public ResponseEntity<?> pausarViaje(@PathVariable Long id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(viajeService.pausarViaje(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

    @PatchMapping("/{id}/reanudar")
    public ResponseEntity<?> reanudarViaje(@PathVariable Long id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(viajeService.reanudarViaje(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

}
