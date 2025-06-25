package org.example.msvcreporte.services;

import org.example.msvcreporte.clients.CuentaClient;
import org.example.msvcreporte.clients.UserClient;
import org.example.msvcreporte.clients.ViajeClient;
import org.example.msvcreporte.dto.ReporteUsuarioActivoDTO;
import org.example.msvcreporte.models.Cuenta;
import org.example.msvcreporte.models.Viaje;
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
    private final CuentaClient cuentaClient;

    public ReporteUsuariosActivosService(UserClient userClient, ViajeClient viajeClient, CuentaClient cuentaClient) {
        this.userClient = userClient;
        this.viajeClient = viajeClient;
        this.cuentaClient = cuentaClient;
    }

    public List<ReporteUsuarioActivoDTO> obtenerUsuariosQueMasViajan(String tipoUsuario, LocalDate inicio, LocalDate fin) {

        // Traer los usuarios del tipo solicitado
        List<Cuenta> usuarios = cuentaClient.getUsuariosPorTipo(tipoUsuario);

        // Transformar en modelos con contador inicial
        List<ReporteUsuarioActivoDTO> modelos = usuarios.stream()
                .map(u -> new ReporteUsuarioActivoDTO(u.getId(), u.getTipoCuenta(), 0))
                .toList();

        // Traer los viajes en ese período
        List<Viaje> viajes = viajeClient.obtenerHistorialPorUsuarioYPeriodo(null, inicio.toString(), fin.toString());

        // Para cada viaje, si el usuario está en la lista, le sumamos uno
        for (Viaje viaje : viajes) {
            for (ReporteUsuarioActivoDTO modelo : modelos) {
                if (modelo.getIdUsuario().equals(viaje.getIdUsuario())) {
                    modelo.setCantidadViajes(modelo.getCantidadViajes() + 1);
                    break;
                }
            }
        }

        // Filtramos los que al menos viajaron una vez y ordenamos
        return modelos.stream()
                .filter(u -> u.getCantidadViajes() > 0)
                .sorted(Comparator.comparingInt(ReporteUsuarioActivoDTO::getCantidadViajes).reversed())
                .toList();
    }
}
