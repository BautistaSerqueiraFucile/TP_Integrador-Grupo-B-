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
    private AdminRepository repository;

    public Admin asignarRolAdmin(String userId) {
        if (repository.existsByUserId(userId)) {
            throw new IllegalArgumentException("El usuario ya existe");
        }
        Admin admin = new Admin();
        admin.setUserId(userId);
        admin.setFechaAsignacion(LocalDateTime.now());
        admin.setActivo(true);
        return repository.save(admin);
    }

    public void quitarRolAdmin(String userId) {
        Optional<Admin> adminOpt = repository.findByUserId(userId);
        adminOpt.ifPresent(repository::delete);
    }

    public Optional<Admin> obtenerPorUserId(String userId) {
        return repository.findByUserId(userId);
    }

    public List<Admin> litarTodosAdmin(Boolean soloActivos){
        List<Admin> todos = repository.findAll();
        if (soloActivos != null && soloActivos){
            return todos.stream().filter(Admin::isActivo).toList();
        }
        return todos;
    }
}
