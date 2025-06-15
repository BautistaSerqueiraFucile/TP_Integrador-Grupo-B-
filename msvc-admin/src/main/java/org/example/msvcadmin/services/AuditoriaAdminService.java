package org.example.msvcadmin.services;

import org.example.msvcadmin.entities.AuditoriaAdmin;
import org.example.msvcadmin.repositories.AuditoriaAdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuditoriaAdminService {

    @Autowired
    private AuditoriaAdminRepository auditoriaAdminRepository;

    public AuditoriaAdmin registrarAccion(UUID adminId, String tipoAccion, String entidadAfectadaId, String detalle) {
        AuditoriaAdmin auditoria = new AuditoriaAdmin();
        auditoria.setAdminId(adminId);
        auditoria.setTipoAccion(tipoAccion);
        auditoria.setEntidadAfectadaId(entidadAfectadaId);
        auditoria.setDetalle(detalle);
        auditoria.setFecha(LocalDateTime.now());
        return auditoriaAdminRepository.save(auditoria);
    }

    public List<AuditoriaAdmin> listarTodas() {
        return auditoriaAdminRepository.findAll();
    }

    public List<AuditoriaAdmin> buscarPorAdmin(UUID adminId) {
        return auditoriaAdminRepository.findByAdminId(adminId);
    }

    public List<AuditoriaAdmin> buscarPorAccion(String tipoAccion) {
        return auditoriaAdminRepository.findByTipoAccion(tipoAccion);
    }

    public List<AuditoriaAdmin> buscarConFiltros(UUID adminId, String tipoAccion, LocalDateTime desde, LocalDateTime hasta) {
        List<AuditoriaAdmin> base = auditoriaAdminRepository.findAll();

        return base.stream()
                .filter(a -> adminId == null || a.getAdminId().equals(adminId))
                .filter(a -> tipoAccion == null || a.getTipoAccion().equalsIgnoreCase(tipoAccion))
                .filter(a -> desde == null || !a.getFecha().isBefore(desde))
                .filter(a -> hasta == null || !a.getFecha().isAfter(hasta))
                .toList();
    }

    public Optional<AuditoriaAdmin> obtenerPorId(UUID id) {
        return auditoriaAdminRepository.findById(id);
    }
}
