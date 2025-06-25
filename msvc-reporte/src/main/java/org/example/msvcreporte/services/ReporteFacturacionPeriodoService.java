package org.example.msvcreporte.services;

import org.example.msvcreporte.clients.FacturacionClient;
import org.example.msvcreporte.dto.ReporteFacturacionPeriodoDTO;
import org.example.msvcreporte.models.Factura;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReporteFacturacionPeriodoService {

    private final FacturacionClient facturacionClient;

    public ReporteFacturacionPeriodoService(FacturacionClient facturacionClient) {
        this.facturacionClient = facturacionClient;
    }

    public ReporteFacturacionPeriodoDTO generarReporte(Long idUsuario, LocalDate fechaDesde, LocalDate fechaHasta) {


        // Simulación de respuesta: suponemos que devuelve un número
        ResponseEntity<List<Factura>> facturas = facturacionClient.obtenerTotalFacturado(idUsuario, fechaDesde, fechaHasta);
        double total = 0;
        for(Factura factura : facturas.getBody()){
            total +=factura.getPrecioViaje();
        }
        if(idUsuario==null){
            idUsuario=-1L;
        }
        return new ReporteFacturacionPeriodoDTO(idUsuario, fechaDesde, fechaHasta, total);
    }
}
