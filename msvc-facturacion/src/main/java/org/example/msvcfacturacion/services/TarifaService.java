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
    public Tarifa updateTarifa(String tipoTarifa, double nuevoValor) {
        Tarifa tarifa = findAll();
        switch (tipoTarifa.toUpperCase()) {
            case "BASICA":
                tarifa.setTarifaBasica(nuevoValor);
                break;
            case "PREMIUM":
                tarifa.setTarifaPremium(nuevoValor);
                break;
            case "PAUSA":
                tarifa.setTarifaPausa(nuevoValor);
                break;
        }
        return save(tarifa);
    }


}
