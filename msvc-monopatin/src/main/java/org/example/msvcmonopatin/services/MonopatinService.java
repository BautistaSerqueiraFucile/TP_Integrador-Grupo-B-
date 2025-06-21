package org.example.msvcmonopatin.services;

import org.example.msvcmonopatin.DTO.EstadoDTO;
import org.example.msvcmonopatin.entities.Monopatin;
import org.example.msvcmonopatin.entities.Ubicacion;
import org.example.msvcmonopatin.repositories.MonopatinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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


    public ResponseEntity<Monopatin> actualizarMonopatin(String id, Monopatin datosActualizados) {
        Optional<Monopatin> optional = monopatinRepository.findById(id);
        if (optional.isPresent()) {
            Monopatin monopatin = optional.get();
            monopatin.setTiempoDeUso(datosActualizados.getTiempoDeUso() );
            monopatin.setTiempoDePausa(datosActualizados.getTiempoDePausa() );
            monopatin.setKilometrosActuales(datosActualizados.getKilometrosActuales());
            monopatin.setEstado(datosActualizados.getEstado());
            monopatin.setUbicacion(datosActualizados.getUbicacion());
            monopatin.setParadaActual(datosActualizados.getParadaActual());
            return ResponseEntity.ok(monopatinRepository.save(monopatin));
        } else {
            return ResponseEntity.notFound().build();
        }

    }


    public ResponseEntity<Monopatin> actualizarEstado(String id, String estado) {
        Optional<Monopatin> optional = monopatinRepository.findById(id);
        if (optional.isPresent()) {
            Monopatin monopatin = optional.get();
            monopatin.setEstado(estado);
            return ResponseEntity.ok(monopatinRepository.save(monopatin));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public EstadoDTO obtenerEstado(String id) {
        Optional<Monopatin> optional = monopatinRepository.findById(id);
        EstadoDTO estadoDTO = new EstadoDTO();
        if (optional.isPresent()) {
            Monopatin monopatin = optional.get();
            estadoDTO.setEstado(monopatin.getEstado());

            return estadoDTO;
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Monopatin no encontrado");
        }
    }

    public Ubicacion obtenerUbicacion(String id) {
        Optional<Monopatin> optional = monopatinRepository.findById(id);
        if (optional.isPresent()) {
            return optional.get().getUbicacion();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Monopatin no encontrado");
        }
    }

    public ResponseEntity<List<Monopatin>> obtenerMonopatinesPorKilometro() {
        return ResponseEntity.ok(monopatinRepository.findAllByOrderByKilometrosActualesDesc());
    }

    public ResponseEntity<Monopatin> actualizarTiempos(String id, double tiempoDeUso, double tiempoPausa,double kilometros) {
        Optional<Monopatin> optional = monopatinRepository.findById(id);
        if (optional.isPresent()) {
            Monopatin monopatin = optional.get();
            monopatin.setTiempoDeUso(monopatin.getTiempoDeUso() + tiempoDeUso);
            monopatin.setTiempoDePausa(monopatin.getTiempoDePausa() + tiempoPausa);
            monopatin.setKilometrosActuales(monopatin.getKilometrosActuales() + kilometros);
            return ResponseEntity.ok(monopatinRepository.save(monopatin));
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Monopatin no encontrado");
        }
    }

    public void guardarLote(List<Monopatin> monopatines) {
        monopatinRepository.saveAll(monopatines);

    }
}

