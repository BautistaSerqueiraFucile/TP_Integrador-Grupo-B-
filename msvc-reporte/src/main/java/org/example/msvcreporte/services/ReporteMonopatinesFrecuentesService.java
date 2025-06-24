package org.example.msvcreporte.services;

import org.example.msvcreporte.clients.ScooterClient;
import org.example.msvcreporte.clients.ViajeClient;
import org.example.msvcreporte.dto.ReporteMonopatinFrecuenteDTO;
import org.example.msvcreporte.models.Monopatin;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ReporteMonopatinesFrecuentesService {

    private final ScooterClient scooterClient;
    private final ViajeClient viajeClient;

    public ReporteMonopatinesFrecuentesService(ScooterClient scooterClient, ViajeClient viajeClient) {
        this.scooterClient = scooterClient;
        this.viajeClient = viajeClient;
    }
//esto
    public List<ReporteMonopatinFrecuenteDTO> generarReporte(int anio, int minViajes) {
        List<Monopatin> monopatines = scooterClient.obtenerTodosLosScooters();

        List<ReporteMonopatinFrecuenteDTO> resultados = new ArrayList<>();

        for (Monopatin mono : monopatines) {
            String id = mono.getId();

            List<Map<String, Object>> viajes = viajeClient.obtenerViajesPorMonopatinYAno(id, anio);
            int cantidad = viajes.size();

            if (cantidad >= minViajes) {
                resultados.add(new ReporteMonopatinFrecuenteDTO(id, cantidad));
            }
        }

        return resultados;
    }
}
