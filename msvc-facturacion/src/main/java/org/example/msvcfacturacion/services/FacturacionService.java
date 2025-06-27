package org.example.msvcfacturacion.services;

import org.example.msvcfacturacion.clients.CuentaClient;
import org.example.msvcfacturacion.dtos.FacturaRequestDTO;
import org.example.msvcfacturacion.entities.Factura;
import org.example.msvcfacturacion.entities.Tarifa;
import org.example.msvcfacturacion.models.Cuenta;
import org.example.msvcfacturacion.repositories.FacturacionRepository;
import org.example.msvcfacturacion.repositories.TarifaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class FacturacionService {

    @Autowired
    private FacturacionRepository repoFacturacion;

    @Autowired
    private TarifaRepository repoTarifa;

    @Autowired
    private CuentaClient cuentaClient;

    @Transactional
    public List<Factura> findAll() throws Exception {
        return repoFacturacion.findAll();
    }

    @Transactional
    public Factura save(FacturaRequestDTO datos) throws Exception {
        Factura factura = new Factura(datos);
        setPreciosTarifas(factura);
        double costoTarifa = factura.getCostoTarifa();
        double tarifaPausa = factura.getTarifaPausa();

        factura.setPrecioViaje(calcularCostoTotal(datos.getTiempoTotal(), datos.getTiempoPausa(), costoTarifa, tarifaPausa));
        cobrar(factura);
        return repoFacturacion.save(factura);
    }

    @Transactional
    public Factura findById(Long id) {
        return repoFacturacion.findById(id).get();
    }

    @Transactional
    public List<Factura> findDate(LocalDate fechaIni, LocalDate fechaFin) {
        return repoFacturacion.findByFechaBetween(fechaIni, fechaFin);
    }

    @Transactional
    public void cobrar(Factura factura) throws Exception {
        Cuenta cuenta = cuentaClient.obtenerCuentaPorId(factura.getIdUsuario());
        cuenta.setSaldo(cuenta.getSaldo() - (factura.getPrecioViaje()));
        cuentaClient.actualizarCuenta(cuenta.getId(), cuenta);
    }

    private String getTipoCuenta(Long id) {
        Cuenta cuenta = cuentaClient.obtenerCuentaPorId(id);
        return cuenta.getTipoCuenta();
    }

    private double calcularCostoTotal(double tiempoTotal, double tiempoPausa, double tipoTarifa, double tarifaPausa) {
        double costoViaje = tiempoTotal * tipoTarifa;
        if (tiempoPausa > 15.0) {
            costoViaje += ((tiempoTotal / 2) * tarifaPausa);
        }
        return costoViaje;
    }

    private void setPreciosTarifas(Factura factura) {
        Optional<Tarifa> opt = repoTarifa.findById(1L);
        Tarifa tarifa = opt.get();

        double porcentajeAumento = tarifa.getPorcentajeAumento();
        double extra = 0;
        LocalDate fechaAumento = tarifa.getFechaAumento();

        if (fechaAumento.isBefore(factura.getFecha())) {
            extra = porcentajeAumento;
        }

        factura.setTarifaPausa(tarifa.getTarifaPausa());
        switch (getTipoCuenta(factura.getIdUsuario()).toUpperCase()) {
            case "BASICA":
                factura.setTipoCuenta("BASICA");
                factura.setCostoTarifa(tarifa.getTarifaBasica() + tarifa.getTarifaBasica() * extra);
                break;
            case "PREMIUM":
                factura.setTipoCuenta("PREMIUM");
                factura.setCostoTarifa(tarifa.getTarifaPremium() + tarifa.getTarifaBasica() * extra);
                break;
        }
    }
}
