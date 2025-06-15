package org.example.msvcadmin.repositories;

import org.example.msvcadmin.entities.AuditoriaAdmin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface AuditoriaAdminRepository extends JpaRepository<AuditoriaAdmin, UUID> {
    List<AuditoriaAdmin> findByAdminId(UUID adminId);
    List<AuditoriaAdmin> findByTipoAccion(String tipoAccion);
}
