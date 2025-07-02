package org.example.msvcfacturacion.repositories;

import org.example.msvcfacturacion.entities.Factura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Repository
public interface FacturacionRepository extends JpaRepository<Factura, Long> {

    List<Factura> findByFechaBetween(
            LocalDate fechaAfter,
            LocalDate fechaBefore
    );
}
