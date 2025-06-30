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

import java.time.LocalDate;
import java.util.List;

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

    @GetMapping("historial")
    public ResponseEntity<?> getViajesPorUsuarioYPeriodo(
            @RequestParam(required = false) Long idUsuario,
            @RequestParam(required = false) String fechaDesde,
            @RequestParam(required = false) String fechaHasta
    ) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(viajeService.obtenerViajesPorUsuarioYPeriodo(idUsuario, fechaDesde, fechaHasta));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

    @PostMapping("")
    public ResponseEntity<?> save(@Valid @RequestBody ViajeRequestDTO entity){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(viajeService.save(new Viaje(entity)));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error. No se pudo ingresar, revise los campos e intente nuevamente.\"}\n"+ e.getMessage());
        }
    }

    @PutMapping("/{id}/finalizar")
    public ResponseEntity<?> finalizarViaje(@PathVariable Long id, @RequestBody FinalizarViajeDTO dto) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(viajeService.finalizarViaje(id, dto));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

    @PutMapping("/{id}/pausar")
    public ResponseEntity<?> pausarViaje(@PathVariable Long id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(viajeService.pausarViaje(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

    @PutMapping("/{id}/reanudar")
    public ResponseEntity<?> reanudarViaje(@PathVariable Long id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(viajeService.reanudarViaje(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(viajeService.deleteById(id) + "\n eliminado correctamente"); // 204: eliminado correctamente
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }
    @GetMapping("monopatines")
    public ResponseEntity<?> getMonopatines(
            @RequestParam("minViaje") Long minViaje,
            @RequestParam("anio") Integer anio
    ) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(viajeService.getViajesPorMonopatinyFecha(minViaje,anio));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }


}
