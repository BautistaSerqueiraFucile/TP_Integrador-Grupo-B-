package org.example.msvccuenta.repositories;

import org.example.msvccuenta.entities.Cuenta;
import org.example.msvccuenta.entities.TipoCuenta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CuentaRepository extends JpaRepository<Cuenta, Long> {
    List<Cuenta> findAllByTipoCuenta(TipoCuenta tipoCuenta);
}
