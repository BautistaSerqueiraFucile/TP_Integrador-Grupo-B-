package org.example.msvcviaje.services;

import org.example.msvcviaje.entities.Viaje;
import org.example.msvcviaje.repositories.ViajeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.example.msvcviaje.dtos.FinalizarViajeDTO;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;

@Service
public class ViajeService {

    @Autowired
    private ViajeRepository repoViaje;

    @Transactional(readOnly = true)
    public List<Viaje> findAll() throws Exception {
        return repoViaje.findAll();
    }

    @Transactional(readOnly = true)
    public Viaje findById(Long id) {
        return repoViaje.findById(id)
                .orElseThrow(() -> new RuntimeException("Viaje con ID " + id + " no encontrado"));
    }

    @Transactional
    public Viaje save(Viaje viaje) {
        return repoViaje.save(viaje);
    }

    @Transactional
    public Viaje finalizarViaje(Long id, FinalizarViajeDTO dto) {
        Viaje viaje = findById(id);

        if (!viaje.getEstado().equals("activo") && !viaje.getEstado().equals("pausado")) {
            throw new RuntimeException("No se puede finalizar el viaje porque no está en curso.");
        }

        viaje.setHoraFin(dto.getHoraFin());
        viaje.setIdParadaFin(dto.getIdParadaFin());
        viaje.setTiempoPausa(dto.getTiempoPausa());
        viaje.setEstado("finalizado");

        return repoViaje.save(viaje);
    }

    @Transactional
    public Viaje pausarViaje(Long id) {
        Viaje viaje = findById(id);

        if (!viaje.getEstado().equals("activo")) {
            throw new RuntimeException("Solo se pueden pausar viajes en estado 'activo'.");
        }

        viaje.setEstado("pausado");

        return repoViaje.save(viaje);
    }

    @Transactional
    public Viaje reanudarViaje(Long id) {
        Viaje viaje = findById(id);

        if (!viaje.getEstado().equals("pausado")) {
            throw new RuntimeException("Solo se pueden reanudar viajes en estado 'pausado'.");
        }

        viaje.setEstado("activo");

        return repoViaje.save(viaje);
    }

    @Transactional
    public Viaje deleteById(Long id) throws Exception {
        if (!repoViaje.existsById(id)) {
            throw new Exception("El viaje con ID " + id + " no existe.");
        }
        Viaje viaje = findById(id);
        repoViaje.deleteById(id);
        return viaje;
    }

    @Transactional
    public List<Viaje> obtenerViajesPorUsuarioYPeriodo(Long idUsuario, String fechaDesde, String fechaHasta) throws Exception {
        try {
            LocalDate desde = (fechaDesde != null) ? LocalDate.parse(fechaDesde) : LocalDate.of(1900, 1, 1);
            LocalDate hasta = (fechaHasta != null) ? LocalDate.parse(fechaHasta) : LocalDate.of(3000, 1, 1);

            return repoViaje.findByIdUsuarioAndFechaBetweenOrderByFechaAscHoraInicioAsc(idUsuario, desde, hasta);
        } catch (DateTimeParseException e) {
            throw new Exception("Formato de fecha inválido. Usar yyyy-MM-dd.");
        }
    }
}
