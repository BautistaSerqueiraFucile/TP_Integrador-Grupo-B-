package org.example.msvcreporte.services;

import org.example.msvcreporte.entities.Reporte;
import org.example.msvcreporte.repositories.ReporteRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReporteService {

    private final ReporteRepository reporteRepository;

    public ReporteService(ReporteRepository reporteRepository) {
        this.reporteRepository = reporteRepository;
    }

    public List<Reporte> obtenerTodos() {
        return reporteRepository.findAll();
    }

    public Reporte guardarReporte(String tipo, String parametros, String resultado, String solicitadoPor) {
        Reporte r = new Reporte(
                LocalDateTime.now(),
                tipo,
                parametros,
                resultado,
                solicitadoPor,
                "OK",
                null
        );
        return reporteRepository.save(r);
    }
}
