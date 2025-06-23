package org.example.msvcreporte.repositories;

import org.example.msvcreporte.entities.Reporte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReporteRepository extends JpaRepository<Reporte, Long> {
    List<Reporte> findByTipo(String tipo);
    List<Reporte> findBySolicitadoPor(String solicitadoPor);
    List<Reporte> findByEstado(String estado);
}
