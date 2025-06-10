package org.example.msvccuenta.repositories;

import org.example.msvccuenta.entities.Cuenta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CuentaRepository extends JpaRepository<Cuenta, Long> {
}
