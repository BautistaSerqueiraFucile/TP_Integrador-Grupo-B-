package org.example.msvcreporte.services;

import org.example.msvcreporte.clients.FacturacionClient;
import org.example.msvcreporte.dto.ReporteFacturacionPeriodoDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Service
public class ReporteFacturacionPeriodoService {

    private final FacturacionClient facturacionClient;

    public ReporteFacturacionPeriodoService(FacturacionClient facturacionClient) {
        this.facturacionClient = facturacionClient;
    }

    public ReporteFacturacionPeriodoDTO generarReporte(int anio, int mesDesde, int mesHasta) {
        // Armamos fechas
        LocalDate fechaDesde = LocalDate.of(anio, mesDesde, 1);
        LocalDate fechaHasta = LocalDate.of(anio, mesHasta, fechaDesde.withMonth(mesHasta).lengthOfMonth());

        // Simulación: llamada a facturación
        // En una implementación real usarías:
        // GET http://facturacion/facturas?fechaDesde=...&fechaHasta=...

        String url = "http://localhost:8085/facturas?fechaDesde={desde}&fechaHasta={hasta}";

        Map<String, String> params = new HashMap<>();
        params.put("desde", fechaDesde.toString());
        params.put("hasta", fechaHasta.toString());

        // Simulación de respuesta: suponemos que devuelve un número
        Double total = facturacionClient.obtenerTotalFacturado(fechaDesde.toString(), fechaHasta.toString());

        return new ReporteFacturacionPeriodoDTO(anio, mesDesde, mesHasta, total != null ? total : 0.0);
    }
}
