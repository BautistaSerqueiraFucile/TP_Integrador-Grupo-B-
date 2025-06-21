package org.example.msvcmonopatin.controller;

import org.example.msvcmonopatin.DTO.EstadoDTO;
import org.example.msvcmonopatin.entities.Monopatin;
import org.example.msvcmonopatin.entities.Ubicacion;
import org.example.msvcmonopatin.repositories.MonopatinRepository;
import org.example.msvcmonopatin.services.MonopatinService;
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

    @Autowired // Inyecta la instancia del repositorio
    private MonopatinService monopatinService;


    @PostMapping
    public ResponseEntity<Monopatin> crearMonopatin(@RequestBody Monopatin monopatin) {
        Monopatin nuevoMonopatin = monopatinService.crearMonopatin(monopatin);
        return new ResponseEntity<>(nuevoMonopatin, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Monopatin>> obtenerTodosMonopatines() {
        List<Monopatin> monopatines = monopatinService.obtenerMonopatines();
        return new ResponseEntity<>(monopatines, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Monopatin> obtenerMonopatinPorId(@PathVariable String id) {
        Optional<Monopatin> monopatin = monopatinService.obtenerMonopatinPorId(id);
        return monopatin.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable String id) {
        return ResponseEntity.ok().body(monopatinService.eliminar(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Monopatin> actualizarMonopatin(@PathVariable String id, @RequestBody Monopatin datosActualizados) {
        ResponseEntity<Monopatin> actualizado = monopatinService.actualizarMonopatin(id, datosActualizados);

        return actualizado;
    }

    @PatchMapping("/{id}/{estado}")
    public ResponseEntity<Monopatin> actualizarEstado(@PathVariable("id") String id,@PathVariable("estado") String estado) {
        return monopatinService.actualizarEstado(id,estado);

    }

    @GetMapping("/{id}/estado")
    public EstadoDTO obtenerEstado(@PathVariable String id) {
        return monopatinService.obtenerEstado(id);
    }

    @GetMapping("/{id}/ubicacion")
    public Ubicacion obtenerUbicacionPorId(@PathVariable String id) {
        return monopatinService.obtenerUbicacion(id);
    }

    @GetMapping("/porKilometro")
    public ResponseEntity<List<Monopatin>> obtenerMonopatinesPorKilometro() {
        return monopatinService.obtenerMonopatinesPorKilometro();
    }

    @PatchMapping("{id}/tiemposYKilometros")
    public void actualizarTiempos(@PathVariable("id") String id,@RequestParam double tiempoDeUso, @RequestParam double tiempoPausa,@RequestParam double kilometros) {
        monopatinService.actualizarTiempos(id,tiempoDeUso,tiempoPausa,kilometros);

    }

    @PostMapping("/lote")
    public ResponseEntity<?> agregarMonopatines(@RequestBody List<Monopatin> monopatines) {
        monopatinService.guardarLote(monopatines);
        return ResponseEntity.ok("Monopatines guardados correctamente");
    }


}