package org.example.msvcparada.repository;

import org.example.msvcparada.entities.Parada;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface ParadaRepository extends JpaRepository<Parada, Long> {
}
