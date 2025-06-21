package org.example.msvcviaje.services;

import org.example.msvcviaje.clients.MonopatinClient;
import org.example.msvcviaje.clients.ParadaClient;
import org.example.msvcviaje.entities.Viaje;
import org.example.msvcviaje.repositories.ViajeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.example.msvcviaje.dtos.FinalizarViajeDTO;

import java.sql.Time;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Year;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;

@Service
public class ViajeService {

    @Autowired
    private ViajeRepository repoViaje;

    @Autowired
    private ParadaClient paradaClient;

    @Autowired
    private MonopatinClient monopatinClient;

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
    public Viaje save(Viaje viaje) throws Exception {
        this.setKilometros(viaje);
        return repoViaje.save(viaje);
    }

    @Transactional
    public Viaje finalizarViaje(Long id, FinalizarViajeDTO dto) throws Exception {
        Viaje viaje = findById(id);

        if (!viaje.getEstado().equals("activo") && !viaje.getEstado().equals("pausado")) {
            throw new RuntimeException("No se puede finalizar el viaje porque no está en curso.");
        }

        viaje.setHoraFin(dto.getHoraFin());
        viaje.setIdParadaFin(dto.getIdParadaFin());
        viaje.setTiempoPausa(dto.getTiempoPausa());
        viaje.setEstado("finalizado");

        double tiempoTotal = this.calcularTiempo(viaje);
        double tiempoPausa = viaje.getTiempoPausa();
        double kilometros = viaje.getKilometros();
        monopatinClient.tiemposYKilometros(viaje.getIdMonopatin(), tiempoTotal, tiempoPausa, kilometros);

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

    private void setKilometros(Viaje viaje) throws Exception {
        Long idParadaInicio = viaje.getIdParadaInicio();
        Long idParadaFin = viaje.getIdParadaFin();
        double distancia = paradaClient.getKilometros(idParadaInicio, idParadaFin);
        viaje.setKilometros(distancia);
    }

    private double calcularTiempo(Viaje viaje) throws Exception {
        LocalTime horaInicio = viaje.getHoraInicio();
        LocalTime horaFin = viaje.getHoraFin();

        return Duration.between(horaInicio, horaFin).toMinutes();
    }

    @Transactional
    public Long getViajesPorMonopatinyFecha(String id_monopatin, LocalDate fechaini, LocalDate fechafin) throws Exception {
        return repoViaje.getCantidadViajesPorMonopatin(id_monopatin, fechaini, fechafin);
    }
}
