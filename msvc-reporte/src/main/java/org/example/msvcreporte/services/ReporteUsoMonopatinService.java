package org.example.msvcreporte.services;

import org.example.msvcreporte.clients.ScooterClient;
import org.example.msvcreporte.clients.ViajeClient;
import org.example.msvcreporte.dto.ReporteUsoMonopatinDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ReporteUsoMonopatinService {

    private final ScooterClient scooterClient;
    private final ViajeClient viajeClient;

    public ReporteUsoMonopatinService(ScooterClient scooterClient, ViajeClient viajeClient) {
        this.scooterClient = scooterClient;
        this.viajeClient = viajeClient;
    }

    public List<ReporteUsoMonopatinDTO> generarReporte(Boolean incluirPausas) {
        List<Map<String, Object>> monopatines = scooterClient.obtenerTodosLosScooters();

        List<ReporteUsoMonopatinDTO> reportes = new ArrayList<>();

        for (Map<String, Object> monopatin : monopatines) {
            Long id = Long.valueOf(monopatin.get("id").toString());
            double kmRecorridos = Double.parseDouble(monopatin.get("kmRecorridos").toString());

            List<Map<String, Object>> viajes = viajeClient.obtenerViajesPorMonopatin(id);

            long tiempoTotal = 0;
            long tiempoConPausas = 0;
            long tiempoSinPausas = 0;

            for (Map<String, Object> viaje : viajes) {
                tiempoTotal += Long.parseLong(viaje.get("duracionTotal").toString());
                tiempoConPausas += Long.parseLong(viaje.get("duracionConPausas").toString());
                tiempoSinPausas += Long.parseLong(viaje.get("duracionSinPausas").toString());
            }

            int cantidadViajes = viajes.size();
            boolean requiereMantenimiento = kmRecorridos > 100 || cantidadViajes > 30;

            reportes.add(new ReporteUsoMonopatinDTO(
                    id,
                    kmRecorridos,
                    tiempoTotal,
                    incluirPausas ? tiempoConPausas : null,
                    incluirPausas ? null : tiempoSinPausas,
                    requiereMantenimiento,
                    cantidadViajes
            ));
        }

        return reportes;
    }
}
