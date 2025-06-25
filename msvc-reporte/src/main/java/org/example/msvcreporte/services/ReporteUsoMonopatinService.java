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

    public List<ReporteUsoMonopatinDTO> generarReporte(Boolean incluirPausas) {
        List<Monopatin> monopatines = scooterClient.obtenerTodosLosScooters();

        List<ReporteUsoMonopatinDTO> reportes = new ArrayList<>();

//        for (Monopatin monopatin : monopatines) {
//           String id = monopatin.getId();
//            double kmRecorridos = monopatin.getKilometrosActuales();
//
//            List<Viaje> viajes = viajeClient.obtenerViajesPorMonopatin(id).getBody();
//
//            long tiempoTotal = 0;
//            long tiempoConPausas = 0;
//            long tiempoSinPausas = 0;
//
//            for (Viaje viaje : viajes) {
//
//            }
//
//            int cantidadViajes = viajes.size();
//            boolean requiereMantenimiento = kmRecorridos > 100 || cantidadViajes > 30;
//
//            reportes.add(new ReporteUsoMonopatinDTO(
//                    id,
//                    kmRecorridos,
//                    tiempoTotal,
//                    incluirPausas ? tiempoConPausas : null,
//                    incluirPausas ? null : tiempoSinPausas,
//                    requiereMantenimiento,
//                    cantidadViajes
//            ));
//        }

        return reportes;
    }
}
