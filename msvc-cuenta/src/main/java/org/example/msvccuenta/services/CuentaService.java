package org.example.msvccuenta.services;

import org.example.msvccuenta.entities.Cuenta;
import org.example.msvccuenta.repositories.CuentaRepository;
import org.springframework.stereotype.Service;
import org.example.msvccuenta.entities.EstadoCuenta;

import java.util.List;
import java.util.Optional;

@Service
public class CuentaService {
    private final CuentaRepository cuentaRepository;

    public CuentaService(CuentaRepository cuentaRepository) {
        this.cuentaRepository = cuentaRepository;
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
}