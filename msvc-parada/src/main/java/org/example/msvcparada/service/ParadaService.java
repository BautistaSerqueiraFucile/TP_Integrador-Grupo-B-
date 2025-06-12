package org.example.msvcparada.service;

import org.example.msvcparada.entities.Parada;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.example.msvcparada.repository.ParadaRepository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ParadaService {
    @Autowired
    private ParadaRepository paradaRepository;

    @Transactional
    public List<Parada> obtenerParadas() {
        return paradaRepository.findAll();
    }

    @Transactional
    public Parada agregar(Parada parada) {
        List<Parada> paradas = obtenerParadas();
        for(Parada p : paradas) {
            if(p.equals(parada)) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Esta parada ya existe");
            }
        }
        return paradaRepository.save(parada);

    }

    @Transactional
    public Optional<Parada> obtenerPorId( Long id) {
        Optional<Parada> parada = paradaRepository.findById(id);
        if (parada.isPresent()) {
            return parada;
        }else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Parada no encontrada");
        }
    }

    @Transactional
    public Parada eliminar(long id) {
        Optional<Parada> parada = obtenerPorId(id);
        if (parada.isPresent()) {
            paradaRepository.deleteById(id);
            return parada.get();
        }else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Parada no encontrada");
        }

    }

    public Parada actualizar(Parada paradaActualizada, long id) {
        Optional<Parada> paradaExistenteOptional = paradaRepository.findById(id);
        Parada paradaExistente;
        if (paradaExistenteOptional.isPresent()) {

            paradaExistente = paradaExistenteOptional.get();
            // 2. Actualizar los campos de la parada existente con los datos de la paradaActualizada
            paradaExistente.setNombre(paradaActualizada.getNombre());
            paradaExistente.setPosX(paradaActualizada.getPosX());
            paradaExistente.setPosY(paradaActualizada.getPosY());
            paradaExistente.setActiva(paradaActualizada.isActiva());
            // No actualizamos el ID, ya que es el identificador de la entidad.

            // 3. Guardar la entidad actualizada.
            // JpaRepository.save() realizará un UPDATE porque la entidad ya tiene un ID no nulo
            // y fue recuperada del contexto de persistencia.

        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Parada no encontrada");
        }
        return paradaRepository.save(paradaExistente);
    }

}
