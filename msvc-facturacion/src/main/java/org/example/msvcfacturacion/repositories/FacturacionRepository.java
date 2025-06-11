package org.example.msvcfacturacion.repositories;

import org.example.msvcfacturacion.entities.Factura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FacturacionRepository extends JpaRepository<Factura, Long> {
}
