package org.example.msvccuenta.repositories;

import org.example.msvccuenta.entities.Cuenta;
import org.example.msvccuenta.entities.TipoCuenta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repositorio para la persistencia y acceso a datos de la entidad {@link Cuenta}.
 * Proporciona métodos CRUD estándar heredados de JpaRepository y consultas personalizadas.
 */
public interface CuentaRepository extends JpaRepository<Cuenta, Long> {
    /**
     * Busca y devuelve todas las cuentas que coinciden con un tipo de cuenta específico.
     *
     * @param tipoCuenta El {@link TipoCuenta} (BASICA o PREMIUM) por el cual filtrar.
     * @return Una lista de {@link Cuenta} que coinciden con el tipo especificado.
     *         Devuelve una lista vacía si no se encuentran coincidencias.
     */
    List<Cuenta> findAllByTipoCuenta(TipoCuenta tipoCuenta);
}
