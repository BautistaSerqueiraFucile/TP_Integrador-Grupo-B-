package org.example.msvcparada.controller;



import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.msvcparada.entities.Parada;
import org.example.msvcparada.service.ParadaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;



import java.util.Optional;
@Tag(name = "Paradas", description = "Operaciones relacionadas con las paradas de monopatines")
@RestController
@RequestMapping("/paradas")
public class ParadaController {

    @Autowired
    private ParadaService paradaService;


    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Obtener parada por ID", description = "Devuelve una parada a partir de su ID")
    @ApiResponse(responseCode = "200", description = "Parada encontrada")
    @ApiResponse(responseCode = "404", description = "Parada no encontrada")
    @GetMapping("/{id}")
    public ResponseEntity<Parada> obtenerPorId(@Parameter(description = "ID de la parada")@PathVariable Long id) {
        return ResponseEntity.ok().body(paradaService.obtenerPorId(id).get());

    }
    @Operation(summary = "Listar todas las paradas", description = "Devuelve todas las paradas registradas")
    @ApiResponse(responseCode = "200", description = "Lista de paradas")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping("")
    public List<Parada> listarParadas() {
        return paradaService.obtenerParadas();
    }

    @Operation(summary = "Crear nueva parada", description = "Agrega una nueva parada al sistema")
    @ApiResponse(responseCode = "202", description = "Parada creada")
    @ApiResponse(responseCode = "409", description = "Parada ya existente")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("")
    public ResponseEntity<Parada> agregar(@RequestBody Parada parada) {
        Parada result = paradaService.agregar(parada);
        return ResponseEntity.accepted().body(result);
    }

    @Operation(summary = "Eliminar parada", description = "Elimina una parada a partir de su ID")
    @ApiResponse(responseCode = "200", description = "Parada eliminada")
    @ApiResponse(responseCode = "404", description = "Parada no encontrada")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@Parameter(description = "ID de la parada a eliminar") @PathVariable Long id) {
        return ResponseEntity.ok().body(paradaService.eliminar(id));
    }

    @Operation(summary = "Actualizar parada", description = "Actualiza los datos de una parada existente")
    @ApiResponse(responseCode = "202", description = "Parada actualizada")
    @ApiResponse(responseCode = "404", description = "Parada no encontrada")
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<Parada> actualizar(@RequestBody Parada parada ,@Parameter(description = "ID de la parada a actualizar")@PathVariable long id) {
        Parada result = paradaService.actualizar(parada,id);
        return ResponseEntity.accepted().body(result);
    }

    @Operation(summary = "Calcular distancia entre dos paradas", description = "Devuelve la distancia en metros entre dos paradas por sus IDs")
    @ApiResponse(responseCode = "200", description = "Distancia calculada correctamente")
    @ApiResponse(responseCode = "404", description = "Una o ambas paradas no fueron encontradas")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping("/distancia")
    public ResponseEntity<?> calcularDistanciaEntreParadas(
            @Parameter(description = "ID de la primera parada")@RequestParam("parada1Id") Long parada1Id, // Recibe el ID de la primera parada
            @Parameter(description = "ID de la segunda parada")@RequestParam("parada2Id") Long parada2Id) { // Recibe el ID de la segunda parada

        double distancia = paradaService.calcularDistancia(parada1Id, parada2Id);
        return ResponseEntity.ok().body(distancia);
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @PostMapping("/lote")
    public ResponseEntity<?> agregarParadas(@RequestBody List<Parada>paradas) {
        paradaService.guardarLote(paradas);
        return ResponseEntity.ok("Paradas guardados correctamente");
    }




}
