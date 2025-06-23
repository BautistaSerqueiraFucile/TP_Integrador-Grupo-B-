package org.example.msvcmonopatin.repositories;


import org.example.msvcmonopatin.entities.Monopatin;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository // Marca esta interfaz como un componente de Spring
public interface MonopatinRepository extends MongoRepository<Monopatin, String> {
    List<Monopatin> findAllByOrderByKilometrosActualesDesc();    // Spring Data MongoDB generará automáticamente las implementaciones de los métodos CRUD:
    // save(), findById(), findAll(), delete(), etc.
    Optional<Monopatin> findFirstByParadaActualAndEstado(String paradaActual, String estado);    // También puedes añadir métodos de búsqueda personalizados si sigues la convención de nombres de Spring Data:
    // List<Monopatin> findByDisponible(boolean disponible);
    // List<Monopatin> findByKilometrajeGreaterThan(double kilometraje);
}