package org.example.msvcviaje.services;

import org.example.msvcviaje.entities.Viaje;
import org.example.msvcviaje.repositories.ViajeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ViajeService {

    @Autowired
    private ViajeRepository repoViaje;

    @Transactional
    public List<Viaje> findAll() throws Exception {
        return repoViaje.findAll();
    }

    @Transactional
    public Viaje findById(Long id) {
        return repoViaje.findById(id).get();
    }

    @Transactional
    public Viaje save(Viaje viaje) throws Exception {
        return repoViaje.save(viaje);
    }
}
