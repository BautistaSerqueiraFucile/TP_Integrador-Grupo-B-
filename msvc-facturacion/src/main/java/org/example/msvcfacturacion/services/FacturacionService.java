package org.example.msvcfacturacion.services;

import org.example.msvcfacturacion.clients.CuentaClient;
import org.example.msvcfacturacion.clients.ViajeClient;
import org.example.msvcfacturacion.entities.Factura;
import org.example.msvcfacturacion.entities.Tarifa;
import org.example.msvcfacturacion.models.Cuenta;
import org.example.msvcfacturacion.models.TiemposViaje;
import org.example.msvcfacturacion.repositories.FacturacionRepository;
import org.example.msvcfacturacion.repositories.TarifaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
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

    @Autowired
    private ViajeClient viajeClient;

    @Transactional
    public List<Factura> findAll() throws Exception {
        return repoFacturacion.findAll();
    }

    @Transactional
    public Factura save(Factura factura) throws Exception {
        TiemposViaje tiempos = viajeClient.getTiemposViaje(factura.getIdViaje());
        setPreciosTarifas(factura);
        double costoTarifa = factura.getCostoTarifa();
        double tarifaPausa = factura.getTarifaPausa();

        factura.setPrecioViaje(calcularCostoTotal(tiempos, costoTarifa, tarifaPausa));

        return repoFacturacion.save(factura);
    }

    @Transactional
    public Factura findById(Long id) {
        return repoFacturacion.findById(id).get();
    }

    @Transactional
    public List<Factura> findByUserAndDate(Long id, LocalDate fechaIni, LocalDate fechaFin){
       return repoFacturacion.findAllByIdUsuarioAndFechaBetweenOrderByFecha(id, fechaIni, fechaFin);
    }

    private String getTipoCuenta(Long id){
        Cuenta cuenta = cuentaClient.obtenerCuentaPorId(id);
        return cuenta.getTipoCuenta();
    }

    private double calcularCostoTotal(TiemposViaje tiempo, double tipoTarifa, double tarifaPausa){
        double costoViaje = tiempo.getTiempoTotal() * tipoTarifa;
        if (tiempo.getTiempoPausa() > 15.0){
            costoViaje += ((tiempo.getTiempoTotal()/2) * tarifaPausa);
        } return costoViaje;
    }

    private void setPreciosTarifas(Factura factura){
        Optional<Tarifa> opt = repoTarifa.findById(1L);
        Tarifa tarifa = opt.get();
        factura.setTarifaPausa(tarifa.getTarifaPausa());
        switch (getTipoCuenta(factura.getIdUsuario()).toUpperCase()){
            case "BASICA":
                factura.setTipoCuenta("BASICA");
                factura.setCostoTarifa(tarifa.getTarifaBasica());
                break;
            case "PREMIUM":
                factura.setTipoCuenta("PREMIUM");
                factura.setCostoTarifa(tarifa.getTarifaPremium());
                break;
        }
    }
}
