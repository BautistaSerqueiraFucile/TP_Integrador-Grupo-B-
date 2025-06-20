package org.example.msvcreporte.services;

import org.example.msvcreporte.clients.CuentaClient;
import org.example.msvcreporte.clients.ViajeClient;
import org.example.msvcreporte.dto.ReporteUsoPorCuentaDTO;
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

    public List<ReporteUsoPorCuentaDTO> generarReporte(Long idCuenta, LocalDate desde, LocalDate hasta, boolean incluirOtros) {
        List<Long> usuarios = cuentaClient.obtenerUsuariosDeCuenta(idCuenta);

        List<ReporteUsoPorCuentaDTO> resultado = new ArrayList<>();

        for (Long idUsuario : usuarios) {

            List<Map<String, Object>> viajes = viajeClient.obtenerHistorialPorUsuarioYPeriodo(
                    idUsuario,
                    desde.toString(),
                    hasta.toString()
            );

            if (!incluirOtros && !idUsuario.equals(idCuenta)) {
                continue; // filtra si solo querés ver al usuario dueño
            }

            double km = viajes.stream()
                    .mapToDouble(v -> Double.parseDouble(v.get("kmRecorridos").toString()))
                    .sum();

            long tiempo = viajes.stream()
                    .mapToLong(v -> Long.parseLong(v.get("duracionTotal").toString()))
                    .sum();

            int cantidad = viajes.size();

            resultado.add(new ReporteUsoPorCuentaDTO(idUsuario, km, tiempo, cantidad));
        }

        return resultado;
    }
}
