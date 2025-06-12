package org.example.msvcmonopatin.repositories;


import org.example.msvcmonopatin.entities.Monopatin;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository // Marca esta interfaz como un componente de Spring
public interface MonopatinRepository extends MongoRepository<Monopatin, String> {
    // Spring Data MongoDB generará automáticamente las implementaciones de los métodos CRUD:
    // save(), findById(), findAll(), delete(), etc.

    // También puedes añadir métodos de búsqueda personalizados si sigues la convención de nombres de Spring Data:
    // List<Monopatin> findByDisponible(boolean disponible);
    // List<Monopatin> findByKilometrajeGreaterThan(double kilometraje);
}