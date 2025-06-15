package org.example.msvcadmin.services;

import org.example.msvcadmin.entities.Admin;
import org.example.msvcadmin.repositories.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    public Admin asignarRolAdmin(String userId) {
        if (adminRepository.existsByUserId(userId)) {
            throw new IllegalArgumentException("El usuario ya existe");
        }
        Admin admin = new Admin();
        admin.setUserId(userId);
        admin.setFechaAsignacion(LocalDateTime.now());
        admin.setActivo(true);
        return adminRepository.save(admin);
    }

    public void quitarRolAdmin(String userId) {
        Optional<Admin> adminOpt = adminRepository.findByUserId(userId);
        adminOpt.ifPresent(adminRepository::delete);
    }

    public Optional<Admin> obtenerPorUserId(String userId) {
        return adminRepository.findByUserId(userId);
    }

    public List<Admin> litarTodosAdmin(Boolean soloActivos){
        List<Admin> todos = adminRepository.findAll();
        if (soloActivos != null && soloActivos){
            return todos.stream().filter(Admin::isActivo).toList();
        }
        return todos;
    }
}
