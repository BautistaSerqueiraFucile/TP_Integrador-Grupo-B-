package org.example.msvcreporte.services;

import org.example.msvcreporte.clients.ScooterClient;
import org.example.msvcreporte.clients.ViajeClient;
import org.example.msvcreporte.dto.ReporteMonopatinFrecuenteDTO;
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

    public List<ReporteMonopatinFrecuenteDTO> generarReporte(int anio, int minViajes) {
        List<Map<String, Object>> monopatines = scooterClient.obtenerTodosLosScooters();

        List<ReporteMonopatinFrecuenteDTO> resultados = new ArrayList<>();

        for (Map<String, Object> monopatin : monopatines) {
            Long id = Long.valueOf(monopatin.get("id").toString());

            List<Map<String, Object>> viajes = viajeClient.obtenerViajesPorMonopatinYAño(id, anio);
            int cantidad = viajes.size();

            if (cantidad >= minViajes) {
                resultados.add(new ReporteMonopatinFrecuenteDTO(id, cantidad));
            }
        }

        return resultados;
    }
}
