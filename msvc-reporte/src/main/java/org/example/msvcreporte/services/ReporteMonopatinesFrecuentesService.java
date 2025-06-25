package org.example.msvcreporte.services;

import org.example.msvcreporte.clients.ScooterClient;
import org.example.msvcreporte.clients.ViajeClient;
import org.example.msvcreporte.dto.ReporteMonopatinFrecuenteDTO;
import org.example.msvcreporte.models.Monopatin;
import org.example.msvcreporte.models.MonopatinViajeDTO;
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
    public List<MonopatinViajeDTO> generarReporte(Integer anio, Long minViajes) {

        return viajeClient.obtenerMinViajesPorMonopatin(minViajes ,anio);
    }
}
