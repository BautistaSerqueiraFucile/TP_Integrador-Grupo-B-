package org.example.msvcadmin.controllers;

import org.example.msvcadmin.entities.Admin;
import org.example.msvcadmin.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/usuarios/{userId}")
    public ResponseEntity<Admin> asignarAdmin(@PathVariable String userId) {
        return ResponseEntity.ok(adminService.asignarRolAdmin(userId));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/usuarios/{userId}")
    public ResponseEntity<Void> quitarAdmin(@PathVariable String userId) {
        adminService.quitarRolAdmin(userId);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/usuarios/{userId}")
    public ResponseEntity<Admin> obtenerAdmin(@PathVariable String userId) {
        return adminService.obtenerPorUserId(userId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/usuarios")
    public ResponseEntity<List<Admin>> listarAdmins(@RequestParam(required = false) Boolean activo) {
        return ResponseEntity.ok(adminService.litarTodosAdmin(activo));
    }
}
