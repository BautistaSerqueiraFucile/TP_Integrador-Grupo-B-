package org.example.msvcreporte.services;

import org.example.msvcreporte.clients.FacturacionClient;
import org.example.msvcreporte.dto.ReporteFacturacionPeriodoDTO;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
public class ReporteFacturacionPeriodoService {

    private final FacturacionClient facturacionClient;

    public ReporteFacturacionPeriodoService(FacturacionClient facturacionClient) {
        this.facturacionClient = facturacionClient;
    }

    public ReporteFacturacionPeriodoDTO generarReporte(int anio, int mesDesde, int mesHasta) {
        // Crear fechas
        LocalDate fechaDesde = LocalDate.of(anio, mesDesde, 1);
        LocalDate fechaHasta = LocalDate.of(anio, mesHasta, LocalDate.of(anio, mesHasta, 1).lengthOfMonth());

        // Llamar al microservicio de facturación
        List<Map<String, Object>> facturas = facturacionClient.obtenerFacturas(
                fechaDesde.toString(),
                fechaHasta.toString(),
                null // sin filtrar por usuario
        );

        // Sumar los montos
        double total = facturas.stream()
                .mapToDouble(f -> Double.parseDouble(f.get("monto").toString()))
                .sum();

        return new ReporteFacturacionPeriodoDTO(anio, mesDesde, mesHasta, total);
    }
}
