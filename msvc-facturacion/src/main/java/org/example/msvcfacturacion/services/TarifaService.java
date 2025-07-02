package org.example.msvcfacturacion.services;

import org.example.msvcfacturacion.entities.Tarifa;
import org.example.msvcfacturacion.repositories.TarifaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TarifaService {

    @Autowired
    private TarifaRepository tarifaRepository;

    @Transactional
    public Tarifa findAll() {
        return tarifaRepository.findById(1L).get();
    }

    @Transactional
    public Tarifa save(Tarifa tarifa) {
        return tarifaRepository.save(tarifa);
    }

    @Transactional
    public Tarifa update(Tarifa nueva) {
        Tarifa fija = findAll();
        fija.setTarifaBasica(nueva.getTarifaBasica());
        fija.setTarifaPremium(nueva.getTarifaPremium());
        fija.setTarifaPausa(nueva.getTarifaPausa());
        fija.setFechaAumento(nueva.getFechaAumento());
        fija.setPorcentajeAumento(nueva.getPorcentajeAumento());
        return tarifaRepository.save(fija);
    }


}
