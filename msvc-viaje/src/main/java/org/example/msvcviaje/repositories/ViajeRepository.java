package org.example.msvcviaje.repositories;

import org.example.msvcviaje.entities.Viaje;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.Year;
import java.util.List;

@Repository
public interface ViajeRepository extends JpaRepository<Viaje, Long> {

    List<Viaje> findByIdUsuarioAndFechaBetween(
            Long idUsuario,
            LocalDate fechaDesde,
            LocalDate fechaHasta
    );

    List<Viaje> findByIdUsuarioAndFechaBetweenOrderByFechaAscHoraInicioAsc(
            Long idUsuario,
            LocalDate fechaDesde,
            LocalDate fechaHasta
    );

    @Query("SELECT COUNT(v.idMonopatin)" +
            "FROM Viaje v " +
            "WHERE v.idMonopatin = :id_buscado AND v.fecha BETWEEN :fechaini AND :fechafin")
    Long getCantidadViajesPorMonopatin(String id_buscado, LocalDate fechaini, LocalDate fechafin);


}
