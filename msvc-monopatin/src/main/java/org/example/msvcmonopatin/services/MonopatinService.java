package org.example.msvcmonopatin.services;

import org.example.msvcmonopatin.entities.Monopatin;
import org.example.msvcmonopatin.repositories.MonopatinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class MonopatinService {

    @Autowired
    private MonopatinRepository monopatinRepository;

    @Transactional
    public Monopatin crearMonopatin(Monopatin monopatin) {

        return monopatinRepository.save(monopatin);
    }

    @Transactional
    public List<Monopatin> obtenerMonopatines() {
        return monopatinRepository.findAll();
    }
    @Transactional
    public Optional<Monopatin> obtenerMonopatinPorId(String id) {
        return monopatinRepository.findById(id);
    }

    @Transactional
    public Object eliminar(String id) {

        Optional<Monopatin> monopatin= obtenerMonopatinPorId(id);
        if (monopatin.isPresent()) {
           monopatinRepository.deleteById(id);
            return monopatin.get();
        }else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Monopatin no encontrado");
        }



    }


    public Optional<Monopatin> actualizarMonopatin(String id, Monopatin datosActualizados) {
        return monopatinRepository.findById(id).map(monopatin -> {
            monopatin.setKilometrosActuales(datosActualizados.getKilometrosActuales());
            monopatin.setEstado(datosActualizados.getEstado());
            monopatin.setUbicacion(datosActualizados.getUbicacion());
            monopatin.setParadaActual(datosActualizados.getParadaActual());
            return monopatinRepository.save(monopatin);
        });
    }


    }

