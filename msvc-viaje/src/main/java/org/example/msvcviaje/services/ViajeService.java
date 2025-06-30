package org.example.msvcviaje.services;

import org.example.msvcviaje.clients.CuentaClient;
import org.example.msvcviaje.clients.FacturacionClient;
import org.example.msvcviaje.clients.MonopatinClient;
import org.example.msvcviaje.clients.ParadaClient;
import org.example.msvcviaje.dtos.MonopatinViajeDTO;
import org.example.msvcviaje.model.FacturaRequestModel;
import org.example.msvcviaje.entities.EstadoViaje;
import org.example.msvcviaje.entities.Viaje;
import org.example.msvcviaje.model.Monopatin;
import org.example.msvcviaje.repositories.ViajeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.example.msvcviaje.dtos.FinalizarViajeDTO;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.List;

@Service
public class ViajeService {

    @Autowired
    private ViajeRepository repoViaje;

    @Autowired
    private ParadaClient paradaClient;

    @Autowired
    private MonopatinClient monopatinClient;

    @Autowired
    private CuentaClient cuentaClient;

    @Autowired
    private FacturacionClient facturaClient;

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
        double saldoActual;
        try {
            saldoActual = cuentaClient.obtenerSaldo(viaje.getIdUsuario());
        } catch (Exception e) {
            throw new Exception("Error al obtener el saldo del viaje" + e.getMessage());
        }
        if (saldoActual > 0) {
            try {
                setMonopatin(viaje);
            } catch (Exception e) {
                throw new Exception("Error monopatin" + e.getMessage());
            }
            return repoViaje.save(viaje);
        } else {
            throw new Exception("Error: Saldo insuficiente");
        }
    }

    @Transactional
    public Viaje finalizarViaje(Long id, FinalizarViajeDTO dto) throws Exception {
        Viaje viaje = findById(id);

        if (!viaje.getEstado().equals(EstadoViaje.activo) && !viaje.getEstado().equals(EstadoViaje.pausado)) {
            throw new RuntimeException("No se puede finalizar el viaje porque no está en curso.");
        }

        viaje.setHoraFin(dto.getHoraFin());
        viaje.setTiempoPausa(dto.getTiempoPausa());
        viaje.setEstado(EstadoViaje.finalizado);
        setKilometros(viaje);

        double tiempoTotal = this.calcularTiempo(viaje);
        double tiempoPausa = viaje.getTiempoPausa();
        double kilometros = viaje.getKilometros();

        FacturaRequestModel datos = new FacturaRequestModel(viaje.getIdUsuario(), viaje.getIdViaje(), viaje.getFecha() ,tiempoTotal, tiempoPausa);

        monopatinClient.tiemposYKilometros(viaje.getIdMonopatin(), tiempoTotal, tiempoPausa, kilometros);
        monopatinClient.modificarEstado(viaje.getIdMonopatin(), "disponible");
        monopatinClient.modificarParada(viaje.getIdMonopatin(), String.valueOf(viaje.getIdParadaFin()));
        facturaClient.postFactura(datos);

        return repoViaje.save(viaje);
    }

    @Transactional
    public Viaje pausarViaje(Long id) {
        Viaje viaje = findById(id);

        if (!viaje.getEstado().equals(EstadoViaje.activo)) {
            throw new RuntimeException("Solo se pueden pausar viajes en estado 'activo'.");
        }

        viaje.setEstado(EstadoViaje.pausado);

        return repoViaje.save(viaje);
    }

    @Transactional
    public Viaje reanudarViaje(Long id) {
        Viaje viaje = findById(id);

        if (!viaje.getEstado().equals(EstadoViaje.pausado)) {
            throw new RuntimeException("Solo se pueden reanudar viajes en estado 'pausado'.");
        }

        viaje.setEstado(EstadoViaje.activo);

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

            if (idUsuario != null) {
                return repoViaje.findByIdUsuarioAndFechaBetweenOrderByFechaAscHoraInicioAsc(idUsuario, desde, hasta);
            } else {
                return repoViaje.findByFechaBetweenOrderByFechaAscHoraInicioAsc(desde, hasta);
            }

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

    private void setMonopatin(Viaje viaje) throws Exception {
        Long idParadaInicio = viaje.getIdParadaInicio();
        Monopatin disponible = monopatinClient.getMonopatinPorParada(idParadaInicio);
        viaje.setIdMonopatin(disponible.getId());
        try {
            monopatinClient.modificarEstado(disponible.getId(), "ocupado");
        } catch (Exception e) {
            throw new RuntimeException("Error " + e.getMessage());
        }
    }

    private double calcularTiempo(Viaje viaje) throws Exception {
        LocalTime horaInicio = viaje.getHoraInicio();
        LocalTime horaFin = viaje.getHoraFin();

        return Duration.between(horaInicio, horaFin).toMinutes();
    }

    @Transactional
    public List<MonopatinViajeDTO> getViajesPorMonopatinyFecha(Long minViaje, Integer anio) throws Exception {
        return repoViaje.obtenerMonopatinesConMasDeXViajes(anio, minViaje);
    }


}
