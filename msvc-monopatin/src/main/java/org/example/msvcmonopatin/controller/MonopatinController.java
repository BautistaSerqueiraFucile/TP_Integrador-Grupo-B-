package org.example.msvcmonopatin.controller;

import org.example.msvcmonopatin.entities.Monopatin;
import org.example.msvcmonopatin.repositories.MonopatinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/monopatines")
public class MonopatinController {

    @Autowired // Inyecta la instancia del repositorio
    private MonopatinRepository monopatinRepository;

    @PostMapping
    public ResponseEntity<Monopatin> crearMonopatin(@RequestBody Monopatin monopatin) {
        Monopatin nuevoMonopatin = monopatinRepository.save(monopatin);
        return new ResponseEntity<>(nuevoMonopatin, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Monopatin>> obtenerTodosMonopatines() {
        List<Monopatin> monopatines = monopatinRepository.findAll();
        return new ResponseEntity<>(monopatines, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Monopatin> obtenerMonopatinPorId(@PathVariable String id) {
        Optional<Monopatin> monopatin = monopatinRepository.findById(id);
        return monopatin.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Puedes agregar más endpoints para actualizar, eliminar, etc.
}
