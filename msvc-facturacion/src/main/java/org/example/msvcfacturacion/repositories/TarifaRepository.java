package org.example.msvcfacturacion.repositories;

import org.example.msvcfacturacion.entities.Tarifa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TarifaRepository  extends JpaRepository<Tarifa, Long> {
}
