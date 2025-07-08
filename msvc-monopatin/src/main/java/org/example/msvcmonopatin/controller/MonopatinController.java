package org.example.msvcmonopatin.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @Operation(summary = "Crear un nuevo monopatín")
    @ApiResponse(responseCode = "201", description = "Monopatín creado exitosamente")
    @PostMapping
    public ResponseEntity<Monopatin> crearMonopatin(@RequestBody Monopatin monopatin) {
        Monopatin nuevoMonopatin = monopatinService.crearMonopatin(monopatin);
        return new ResponseEntity<>(nuevoMonopatin, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @Operation(summary = "Obtener todos los monopatines")
    @ApiResponse(responseCode = "200", description = "Listado obtenido correctamente")
    @GetMapping
    public ResponseEntity<List<Monopatin>> obtenerTodosMonopatines() {
        List<Monopatin> monopatines = monopatinService.obtenerMonopatines();
        return new ResponseEntity<>(monopatines, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @Operation(summary = "Obtener todos los monopatines")
    @ApiResponse(responseCode = "200", description = "Listado obtenido correctamente")
    @GetMapping("/{id}")
    public ResponseEntity<Monopatin> obtenerMonopatinPorId(@PathVariable String id) {
        Optional<Monopatin> monopatin = monopatinService.obtenerMonopatinPorId(id);
        return monopatin.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Eliminar un monopatín")
    @ApiResponse(responseCode = "200", description = "Monopatín eliminado")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable String id) {
        return ResponseEntity.ok().body(monopatinService.eliminar(id));
    }

    @PreAuthorize("hasRole ('USER') or hasRole('ADMIN')")
    @Operation(summary = "Actualizar un monopatín")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Monopatín actualizado"),
            @ApiResponse(responseCode = "404", description = "Monopatín no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Monopatin> actualizarMonopatin(@Parameter(description = "ID del monopatín")@PathVariable String id, @RequestBody Monopatin datosActualizados) {
        ResponseEntity<Monopatin> actualizado = monopatinService.actualizarMonopatin(id, datosActualizados);

        return actualizado;
    }
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Actualizar el estado de un monopatín",
            description = "Permite modificar el estado (por ejemplo: ACTIVO, EN_USO, FUERA_DE_SERVICIO) de un monopatín específico según su ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estado actualizado correctamente"),
            @ApiResponse(responseCode = "404", description = "No se encontró el monopatín con el ID especificado")
    })
    @PutMapping("/estado/{id}/{estado}")
    public ResponseEntity<Monopatin> actualizarEstado(@Parameter(description = "ID del monopatín")@PathVariable("id") String id,@PathVariable("estado") String estado) {
        return monopatinService.actualizarEstado(id,estado);

    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Obtener el estado actual de un monopatín",
            description = "Retorna el estado actual (por ejemplo: ACTIVO, EN_USO, FUERA_DE_SERVICIO) del monopatín identificado por su ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estado obtenido correctamente"),
            @ApiResponse(responseCode = "404", description = "No se encontró el monopatín con el ID especificado")
    })
    @GetMapping("/{id}/estado")
    public EstadoDTO obtenerEstado(@Parameter(description = "ID del monopatín")@PathVariable String id) {
        return monopatinService.obtenerEstado(id);
    }


    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @Operation(
            summary = "Obtener la ubicación actual de un monopatín",
            description = "Devuelve la ubicación actual del monopatín según su ID, si existe."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ubicación obtenida correctamente"),
            @ApiResponse(responseCode = "404", description = "No se encontró el monopatín con el ID especificado")
    })
    @GetMapping("/{id}/ubicacion")
    public Ubicacion obtenerUbicacionPorId(@Parameter(description = "ID del monopatín")@PathVariable String id) {
        return monopatinService.obtenerUbicacion(id);
    }



    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Obtener todos los monopatines ordenados por kilometraje",
            description = "Devuelve una lista de monopatines ordenados de mayor a menor según la cantidad de kilómetros recorridos."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Lista obtenida correctamente"
    )
    @GetMapping("/porKilometro")
    public ResponseEntity<List<Monopatin>> obtenerMonopatinesPorKilometro() {
        return monopatinService.obtenerMonopatinesPorKilometro();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Actualizar tiempos y kilómetros",
            description = "Suma valores al tiempo de uso, tiempo de pausa y kilómetros actuales del monopatín."
    )
    @ApiResponse(responseCode = "200", description = "Tiempos y kilómetros actualizados correctamente")
    @ApiResponse(responseCode = "404", description = "Monopatín no encontrado")
    @PutMapping("{id}/tiemposYKilometros")
    public void actualizarTiempos(@Parameter(description = "ID del monopatín") @PathVariable("id") String id,
                                  @Parameter(description = "Tiempo adicional de uso (en horas)") @RequestParam double tiempoDeUso,
                                  @Parameter(description = "Tiempo adicional de pausa (en horas)") @RequestParam double tiempoPausa,
                                  @Parameter(description = "Kilómetros adicionales recorridos") @RequestParam double kilometros){
        monopatinService.actualizarTiempos(id,tiempoDeUso,tiempoPausa,kilometros);

    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @Operation(
            summary = "Obtener el primer monopatín disponible en una parada",
            description = "Busca el primer monopatín con estado 'disponible' que esté ubicado en la parada especificada."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Monopatín encontrado y devuelto correctamente"),
            @ApiResponse(responseCode = "404", description = "No se encontró ningún monopatín disponible en esa parada")
    })
    @GetMapping("/porParada")
    public ResponseEntity<Monopatin> obtenerMonopatinActivoPorParada(@Parameter(description = "Nombre o ID de la parada", required = true)@RequestParam String parada) {
        return monopatinService.obtenerMonopatinActivoPorParada(parada);

    }
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @Operation(
            summary = "Actualizar parada actual del monopatín",
            description = "Actualiza el campo `paradaActual` del monopatín identificado por su ID."
    )
    @ApiResponse(responseCode = "200", description = "Parada actual actualizada correctamente")
    @ApiResponse(responseCode = "404", description = "Monopatín no encontrado")

    @PutMapping("/{id}/paradaActual")
    public ResponseEntity<Monopatin> actualizarTiempos(@PathVariable("id") String id, @RequestParam String paradaActual) {
        return monopatinService.actualizarParada(id,paradaActual);

    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Resetear kilometraje del monopatín",
            description = "Reinicia a cero el kilometraje actual del monopatín identificado por su ID."
    )
    @ApiResponse(responseCode = "204", description = "Kilometraje reseteado correctamente")
    @ApiResponse(responseCode = "404", description = "Monopatín no encontrado")
    @PutMapping("/{id}/reset-km")
    void resetearKilometraje( @Parameter(description = "ID del monopatín") @PathVariable("id") String id,
    @Parameter(description = "Nueva parada actual del monopatín") @RequestParam String paradaActual){
        monopatinService.resetearKilometraje(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Obtener estadísticas de uso",
            description = "Devuelve una lista de estadísticas con tiempo de uso, pausa y kilometraje total por monopatín."
    )
    @ApiResponse(responseCode = "200", description = "Estadísticas obtenidas correctamente")
    @GetMapping("/estadisticas")
    ResponseEntity<List<MonopatinEstadisticasDTO>> obtnerDatosEstadisticos(){
        return ResponseEntity.ok(monopatinService.obtnerDatosEstadisticos());
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @PostMapping("/lote")
    public ResponseEntity<?> agregarMonopatines(@RequestBody List<Monopatin> monopatines) {
        monopatinService.guardarLote(monopatines);
        return ResponseEntity.ok("Monopatines guardados correctamente");
    }



}