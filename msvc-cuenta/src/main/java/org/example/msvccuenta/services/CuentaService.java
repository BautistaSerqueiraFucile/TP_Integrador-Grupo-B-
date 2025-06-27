package org.example.msvccuenta.services;

import org.example.msvccuenta.entities.Cuenta;
import org.example.msvccuenta.entities.EstadoCuenta;
import org.example.msvccuenta.entities.TipoCuenta;
import org.example.msvccuenta.entities.dto.ParadaDto;
import org.example.msvccuenta.entities.dto.UsuarioDto;
import org.example.msvccuenta.entities.dto.ViajeDto;
import org.example.msvccuenta.exceptions.CuentaNoEncontradaException;
import org.example.msvccuenta.feignClients.ParadaFeignClient;
import org.example.msvccuenta.feignClients.UsuarioFeignClient;
import org.example.msvccuenta.feignClients.ViajeFeignClient;
import org.example.msvccuenta.repositories.CuentaRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class CuentaService {
    private final CuentaRepository cuentaRepository;
    private final ParadaFeignClient paradaFeignClient;
    private final UsuarioFeignClient usuarioFeignClient;
    private final ViajeFeignClient viajeFeignClient;

    public CuentaService(CuentaRepository cuentaRepository,
                         ParadaFeignClient paradaFeignClient,
                         UsuarioFeignClient usuarioFeignClient,
                         ViajeFeignClient viajeFeignClient) {
        this.cuentaRepository = cuentaRepository;
        this.paradaFeignClient = paradaFeignClient;
        this.usuarioFeignClient = usuarioFeignClient;
        this.viajeFeignClient = viajeFeignClient;
    }

    public List<Cuenta> listar() {
        return cuentaRepository.findAll();
    }

    public Cuenta buscarPorId(Long id) {
        if (id <= 0) {
            throw new IllegalArgumentException("El ID debe ser un número positivo.");
        }
        return cuentaRepository.findById(id)
                .orElseThrow(() -> new CuentaNoEncontradaException("Cuenta no encontrada con ID: " + id));
    }

    public Cuenta crear(Cuenta cuenta) {
        return cuentaRepository.save(cuenta);
    }

    public Cuenta actualizar(Long id, Cuenta cuenta) {
        return cuentaRepository.findById(id).map(cuentaDB -> {
            cuentaDB.setFechaAlta(cuenta.getFechaAlta());
            cuentaDB.setTipoCuenta(cuenta.getTipoCuenta());
            cuentaDB.setSaldo(cuenta.getSaldo());
            cuentaDB.setMercadoPagoId(cuenta.getMercadoPagoId());
            cuentaDB.setEstadoCuenta(cuenta.getEstadoCuenta());
            cuentaDB.setKmRecorridosMesPremium(cuenta.getKmRecorridosMesPremium());
            cuentaDB.setUsuariosId(cuenta.getUsuariosId());

            return cuentaRepository.save(cuentaDB);
        }).orElseThrow(() -> new CuentaNoEncontradaException("No se pudo actualizar. Cuenta no encontrada con ID: " + id));
    }

    public void eliminar(Long id) {
        if (!cuentaRepository.existsById(id)) {
            throw new CuentaNoEncontradaException("No se pudo eliminar. Cuenta no encontrada con ID: " + id);
        }
        cuentaRepository.deleteById(id);
    }
    public Cuenta anularCuenta(Long id) {
        Cuenta cuenta = cuentaRepository.findById(id)
                .orElseThrow(() -> new CuentaNoEncontradaException("No se pudo anular. Cuenta no encontrada con ID: " + id));

        cuenta.setEstadoCuenta(EstadoCuenta.ANULADA);
        return cuentaRepository.save(cuenta);
    }

    public Cuenta activarCuenta(Long id) {
        Cuenta cuenta = cuentaRepository.findById(id)
                .orElseThrow(() -> new CuentaNoEncontradaException("No se pudo activar. Cuenta no encontrada con ID: " + id));

        cuenta.setEstadoCuenta(EstadoCuenta.ACTIVA);
        return cuentaRepository.save(cuenta);
    }

    public Double calcularDistanciaAParada(Long idCuenta, Long idParada) {
        Optional<Cuenta> cuentaOpt = cuentaRepository.findById(idCuenta);
        if (cuentaOpt.isEmpty()) return null;
        Cuenta cuenta = cuentaOpt.get();
        if (cuenta.getUsuariosId().isEmpty()) return null;
        Long primerUsuarioId = cuenta.getUsuariosId().iterator().next();

        ParadaDto parada = paradaFeignClient.getParadaById(idParada);
        UsuarioDto usuario = usuarioFeignClient.getUsuarioById(primerUsuarioId);
        if (parada == null || usuario == null) return null;

        double x1 = parada.getPosX();
        double y1 = parada.getPosY();
        double x2 = usuario.getLatitud();
        double y2 = usuario.getLongitud();

        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }

    public Double obtenerSaldo(Long id) {
        return cuentaRepository.findById(id)
                .map(Cuenta::getSaldo)
                .orElseThrow(() -> new CuentaNoEncontradaException("No se pudo obtener el saldo. Cuenta no encontrada con ID: " + id));
    }
    public Cuenta actualizarTipoCuenta(Long id, TipoCuenta tipo) {
        Cuenta cuenta = cuentaRepository.findById(id)
                .orElseThrow(() -> new CuentaNoEncontradaException("No se pudo cambiar el plan. Cuenta no encontrada con ID: " + id));
        cuenta.setTipoCuenta(tipo);
        return cuentaRepository.save(cuenta);
    }

    public Cuenta recargarSaldo(Long id, Double monto) {
        Cuenta cuenta = cuentaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cuenta no encontrada"));
        if (cuenta.getEstadoCuenta() == EstadoCuenta.ANULADA) {
            throw new IllegalStateException("No se puede recargar saldo en una cuenta ANULADA.");
        }
        if (cuenta.getTipoCuenta() == TipoCuenta.PREMIUM) {
            throw new RuntimeException("No se puede recargar saldo en cuentas PREMIUM. Estas pagan una tarifa fija mensual.");
        }
        if (monto <= 0) {
            throw new IllegalArgumentException("El monto de recarga debe ser positivo.");
        }
        cuenta.setSaldo(cuenta.getSaldo() + monto);
        return cuentaRepository.save(cuenta);
    }

    public List<ViajeDto> historialViajes(Long userId) {
        return viajeFeignClient.getViajesPorUsuarioYPeriodo(userId);
    }

    public List<Cuenta> obtenerPorTipo(String tipo) {
        return cuentaRepository.findAllByTipoCuenta(TipoCuenta.valueOf(tipo));
    }

    public ParadaDto paradaMasCercana(Long idCuenta) {
        List<ParadaDto> paradas = paradaFeignClient.listarParadas();
        if (paradas == null || paradas.isEmpty()) {
            throw new IllegalStateException("No se encontraron paradas disponibles.");
        }

        ParadaDto paradaMasCercana = paradas.stream()
                .min((p1, p2) -> Double.compare(
                        calcularDistanciaAParada(idCuenta, p1.getId()),
                        calcularDistanciaAParada(idCuenta, p2.getId())
                ))
                .orElseThrow(() -> new IllegalStateException("No se encontraron paradas disponibles."));

        return paradaMasCercana;
    }
}