package org.example.msvcreporte.services;

import org.example.msvcreporte.clients.ScooterClient;
import org.example.msvcreporte.clients.ViajeClient;
import org.example.msvcreporte.dto.ReporteUsoMonopatinDTO;
import org.example.msvcreporte.models.Monopatin;
import org.example.msvcreporte.models.Viaje;
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

    public List<ReporteUsoMonopatinDTO> generarReporte() {
        return scooterClient.obtenerEstadisticas();

    }
}
