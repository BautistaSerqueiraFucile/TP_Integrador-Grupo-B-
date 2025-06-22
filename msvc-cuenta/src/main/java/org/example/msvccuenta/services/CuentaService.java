package org.example.msvccuenta.services;

import org.example.msvccuenta.entities.Cuenta;
import org.example.msvccuenta.entities.EstadoCuenta;
import org.example.msvccuenta.entities.TipoCuenta;
import org.example.msvccuenta.entities.dto.ParadaDto;
import org.example.msvccuenta.entities.dto.UsuarioDto;
import org.example.msvccuenta.entities.dto.ViajeDto;
import org.example.msvccuenta.feignClients.ParadaFeignClient;
import org.example.msvccuenta.feignClients.UsuarioFeignClient;
import org.example.msvccuenta.feignClients.ViajeFeignClient;
import org.example.msvccuenta.repositories.CuentaRepository;
import org.springframework.stereotype.Service;

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

    public Optional<Cuenta> buscarPorId(Long id) {
        return cuentaRepository.findById(id);
    }

    public Cuenta crear(Cuenta cuenta) {
        return cuentaRepository.save(cuenta);
    }

    public Optional<Cuenta> actualizar(Long id, Cuenta cuenta) {
        return cuentaRepository.findById(id).map(c -> {
            c.setFechaAlta(cuenta.getFechaAlta());
            c.setTipoCuenta(cuenta.getTipoCuenta());
            c.setSaldo(cuenta.getSaldo());
            c.setMercadoPagoId(cuenta.getMercadoPagoId());
            c.setEstadoCuenta(cuenta.getEstadoCuenta());
            c.setKmRecorridosMesPremium(cuenta.getKmRecorridosMesPremium());
            c.setUsuariosId(cuenta.getUsuariosId());
            return cuentaRepository.save(c);
        });
    }

    public boolean eliminar(Long id) {
        if (cuentaRepository.existsById(id)) {
            cuentaRepository.deleteById(id);
            return true;
        }
        return false;
    }
    public Optional<Cuenta> anularCuenta(Long id) {
        return cuentaRepository.findById(id).map(c -> {
            c.setEstadoCuenta(EstadoCuenta.ANULADA);
            return cuentaRepository.save(c);
        });
    }

    public Optional<Cuenta> activarCuenta(Long id) {
        return cuentaRepository.findById(id).map(c -> {
            c.setEstadoCuenta(EstadoCuenta.ACTIVA);
            return cuentaRepository.save(c);
        });
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

    public Optional<Double> obtenerSaldo(Long id) {
        return cuentaRepository.findById(id).map(Cuenta::getSaldo);
    }
    public Optional<Cuenta> actualizarTipoCuenta(Long id, TipoCuenta tipoCuenta) {
        return cuentaRepository.findById(id).map(c -> {
            c.setTipoCuenta(tipoCuenta);
            return cuentaRepository.save(c);
        });
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
}