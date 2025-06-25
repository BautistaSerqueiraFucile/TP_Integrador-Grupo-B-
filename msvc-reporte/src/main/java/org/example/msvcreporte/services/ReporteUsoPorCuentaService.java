package org.example.msvcreporte.services;

import org.example.msvcreporte.clients.CuentaClient;
import org.example.msvcreporte.clients.ViajeClient;
import org.example.msvcreporte.dto.ReporteUsoPorCuentaDTO;
import org.example.msvcreporte.models.Viaje;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ReporteUsoPorCuentaService {

    private final CuentaClient cuentaClient;
    private final ViajeClient viajeClient;

    public ReporteUsoPorCuentaService(CuentaClient cuentaClient, ViajeClient viajeClient) {
        this.cuentaClient = cuentaClient;
        this.viajeClient = viajeClient;
    }

    public List<Viaje> generarReporte(Long idCuenta, LocalDate desde, LocalDate hasta) {
        return viajeClient.obtenerHistorialPorUsuarioYPeriodo(idCuenta, desde.toString(), hasta.toString());
    }
}
