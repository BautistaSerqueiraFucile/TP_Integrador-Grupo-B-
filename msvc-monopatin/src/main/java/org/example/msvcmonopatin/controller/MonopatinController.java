package org.example.msvcmonopatin.controller;

import org.example.msvcmonopatin.DTO.EstadoDTO;
import org.example.msvcmonopatin.DTO.MonopatinEstadisticasDTO;
import org.example.msvcmonopatin.entities.Monopatin;
import org.example.msvcmonopatin.entities.Ubicacion;
import org.example.msvcmonopatin.repositories.MonopatinRepository;
import org.example.msvcmonopatin.services.MonopatinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<Monopatin> crearMonopatin(@RequestBody Monopatin monopatin) {
        Monopatin nuevoMonopatin = monopatinService.crearMonopatin(monopatin);
        return new ResponseEntity<>(nuevoMonopatin, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<Monopatin>> obtenerTodosMonopatines() {
        List<Monopatin> monopatines = monopatinService.obtenerMonopatines();
        return new ResponseEntity<>(monopatines, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<Monopatin> obtenerMonopatinPorId(@PathVariable String id) {
        Optional<Monopatin> monopatin = monopatinService.obtenerMonopatinPorId(id);
        return monopatin.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable String id) {
        return ResponseEntity.ok().body(monopatinService.eliminar(id));
    }

    @PreAuthorize("hasRole ('USER') or hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<Monopatin> actualizarMonopatin(@PathVariable String id, @RequestBody Monopatin datosActualizados) {
        ResponseEntity<Monopatin> actualizado = monopatinService.actualizarMonopatin(id, datosActualizados);

        return actualizado;
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/estado/{id}/{estado}")
    public ResponseEntity<Monopatin> actualizarEstado(@PathVariable("id") String id,@PathVariable("estado") String estado) {
        return monopatinService.actualizarEstado(id,estado);

    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}/estado")
    public EstadoDTO obtenerEstado(@PathVariable String id) {
        return monopatinService.obtenerEstado(id);
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping("/{id}/ubicacion")
    public Ubicacion obtenerUbicacionPorId(@PathVariable String id) {
        return monopatinService.obtenerUbicacion(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/porKilometro")
    public ResponseEntity<List<Monopatin>> obtenerMonopatinesPorKilometro() {
        return monopatinService.obtenerMonopatinesPorKilometro();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("{id}/tiemposYKilometros")
    public void actualizarTiempos(@PathVariable("id") String id,@RequestParam double tiempoDeUso, @RequestParam double tiempoPausa,@RequestParam double kilometros) {
        monopatinService.actualizarTiempos(id,tiempoDeUso,tiempoPausa,kilometros);

    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @PostMapping("/lote")
    public ResponseEntity<?> agregarMonopatines(@RequestBody List<Monopatin> monopatines) {
        monopatinService.guardarLote(monopatines);
        return ResponseEntity.ok("Monopatines guardados correctamente");
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping("/porParada")
    public ResponseEntity<Monopatin> obtenerMonopatinActivoPorParada(@RequestParam String parada) {
        return monopatinService.obtenerMonopatinActivoPorParada(parada);

    }
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @PutMapping("/{id}/paradaActual")
    public ResponseEntity<Monopatin> actualizarTiempos(@PathVariable("id") String id, @RequestParam String paradaActual) {
        return monopatinService.actualizarParada(id,paradaActual);

    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}/reset-km")
    void resetearKilometraje(@PathVariable("id") String id){
        monopatinService.resetearKilometraje(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/estadisticas")
    ResponseEntity<List<MonopatinEstadisticasDTO>> obtnerDatosEstadisticos(){
        return ResponseEntity.ok(monopatinService.obtnerDatosEstadisticos());
    }
}