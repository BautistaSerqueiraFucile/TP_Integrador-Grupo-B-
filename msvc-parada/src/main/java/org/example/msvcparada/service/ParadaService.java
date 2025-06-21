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

    public double calcularDistancia(long parada1Id, long parada2Id) {
        // Obtenemos las coordenadas de cada parada
        Optional<Parada> parada1Op= paradaRepository.findById(parada1Id);
        Optional<Parada> parada2Op= paradaRepository.findById(parada2Id);

        Parada parada1;
        Parada parada2;

        if(parada1Op.isPresent()){
           parada1 = parada1Op.get();
        }else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Parada1 no encontrada");
        }
        if(parada2Op.isPresent()){
            parada2 =  parada2Op.get();
        }else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Parada2 no encontrada");
        }
        return calculadoraDeDistancias(parada1,parada2);
    }
    private double calculadoraDeDistancias(Parada parada1, Parada parada2) {
        double x1;
        double y1;
        double x2;
        double y2;

        x1 = parada1.getPosX();
        y1  = parada1.getPosY();

        x2 = parada2.getPosX();
        y2  = parada2.getPosY();


        // Calculamos la diferencia en X y en Y
        double deltaX = x2 - x1;
        double deltaY = y2 - y1;

        // Fórmula de distancia euclidiana simplificada (sin raíz cuadrada, para evitar ceros si son el mismo punto y simplificar)
        // Multiplicamos por un factor grande para que el resultado sea en "metros"
        // Aseguramos que el resultado sea siempre positivo y mayor que cero si las paradas son diferentes
        double distanciaCalculada = Math.sqrt(deltaX * deltaX + deltaY * deltaY) * 100.0; // Multiplica por 100 para simular metros

        // Si la distancia calculada es 0 (porque las paradas tienen exactamente las mismas coordenadas),
        // queremos que devuelva un valor mínimo positivo para cumplir el requisito "mayor que cero".
        if (distanciaCalculada < 1.0) { // Usamos 1.0 para tener un mínimo, podrías ajustarlo
            return 1.0; // Distancia mínima para paradas en el mismo punto o muy cercanas
        }

        return distanciaCalculada;
    }

    public void guardarLote(List<Parada> paradas) {
        paradaRepository.saveAll(paradas);

    }
}
