package org.example.msvcviaje.repositories;

import feign.Param;
import org.example.msvcviaje.dtos.MonopatinViajeDTO;
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

    @Query("SELECT new org.example.msvcviaje.dtos.MonopatinViajeDTO(v.idMonopatin, YEAR(v.fecha), COUNT(v)) " +
            "FROM Viaje v " +
            "WHERE YEAR(v.fecha) = :anio " +
            "GROUP BY v.idMonopatin, YEAR(v.fecha) " +
            "HAVING COUNT(v) >= :cantidadMinima")
    List<MonopatinViajeDTO> obtenerMonopatinesConMasDeXViajes(@Param("anio") Integer anio,
                                                                              @Param("cantidadMinima") Long cantidadMinima);


    List<Viaje> findByFechaBetweenOrderByFechaAscHoraInicioAsc(
            LocalDate desde,
            LocalDate hasta
    );
}
