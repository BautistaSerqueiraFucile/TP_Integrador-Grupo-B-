package org.example.msvcreporte.services;

import org.example.msvcreporte.clients.UserClient;
import org.example.msvcreporte.clients.ViajeClient;
import org.example.msvcreporte.dto.ReporteUsuarioActivoDTO;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

@Service
public class ReporteUsuariosActivosService {

    private final UserClient userClient;
    private final ViajeClient viajeClient;

    public ReporteUsuariosActivosService(UserClient userClient, ViajeClient viajeClient) {
        this.userClient = userClient;
        this.viajeClient = viajeClient;
    }

    public List<ReporteUsuarioActivoDTO> generarReporte(LocalDate fechaDesde, LocalDate fechaHasta, String tipoUsuario) {
        List<Map<String, Object>> usuarios = userClient.obtenerTodosLosUsuarios();

        List<ReporteUsuarioActivoDTO> resultado = new ArrayList<>();

        for (Map<String, Object> user : usuarios) {
            Long idUsuario = Long.valueOf(user.get("id").toString());
            String tipo = user.get("tipo").toString();

            if (!tipoUsuario.equalsIgnoreCase("todos") && !tipo.equalsIgnoreCase(tipoUsuario)) continue;

            String nombre = user.get("nombre").toString();

            List<Map<String, Object>> viajes = viajeClient.obtenerHistorialPorUsuarioYPeriodo(
                    idUsuario,
                    fechaDesde.toString(),
                    fechaHasta.toString()
            );

            if (viajes.isEmpty()) continue;

            int cantidad = viajes.size();
            double km = viajes.stream()
                    .mapToDouble(v -> Double.parseDouble(v.get("kmRecorridos").toString()))
                    .sum();

            resultado.add(new ReporteUsuarioActivoDTO(idUsuario, nombre, cantidad, km));
        }

        resultado.sort(Comparator.comparing(ReporteUsuarioActivoDTO::getCantidadViajes).reversed());

        return resultado;
    }
}
