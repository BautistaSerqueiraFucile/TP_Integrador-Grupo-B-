package org.example.msvcparada.controller;

import org.example.msvcparada.entities.Parada;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.example.msvcparada.service.ParadaService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/parada")
public class ParadaController {

    @Autowired
    private ParadaService paradaService;

    @GetMapping("/{id}")
    public ResponseEntity<Parada> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok().body(paradaService.obtenerPorId(id).get());

    }

    @GetMapping("")
    public List<Parada> listarParadas() {
        return paradaService.obtenerParadas();
    }
    @PostMapping("")
    public ResponseEntity<Parada> agregar(@RequestBody Parada parada) {
        Parada result = paradaService.agregar(parada);
        return ResponseEntity.accepted().body(result);

    }

    // deveria poder eliminar una parada que tenga monopatines??
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        return ResponseEntity.ok().body(paradaService.eliminar(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Parada> actualizar(@RequestBody Parada parada ,@PathVariable long id) {
        Parada result = paradaService.actualizar(parada,id);
        return ResponseEntity.accepted().body(result);
    }
    @GetMapping("/distancia")
    public ResponseEntity<?> calcularDistanciaEntreParadas(
            @RequestParam("parada1Id") Long parada1Id, // Recibe el ID de la primera parada
            @RequestParam("parada2Id") Long parada2Id) { // Recibe el ID de la segunda parada

        double distancia = paradaService.calcularDistancia(parada1Id, parada2Id);
        return ResponseEntity.ok().body(distancia);
    }
}
