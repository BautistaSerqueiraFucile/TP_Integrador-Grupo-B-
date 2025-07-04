package org.example.msvcadmin.controllers;

import org.example.msvcadmin.entities.Admin;
import org.example.msvcadmin.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping("/usuarios/{userId}")
    public ResponseEntity<Admin> asignarAdmin(@PathVariable String userId) {
        return ResponseEntity.ok(adminService.asignarRolAdmin(userId));
    }

    @DeleteMapping("/usuarios/{userId}")
    public ResponseEntity<Void> quitarAdmin(@PathVariable String userId) {
        adminService.quitarRolAdmin(userId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/usuarios/{userId}")
    public ResponseEntity<Admin> obtenerAdmin(@PathVariable String userId) {
        return adminService.obtenerPorUserId(userId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/usuarios")
    public ResponseEntity<List<Admin>> listarAdmins(@RequestParam(required = false) Boolean activo) {
        return ResponseEntity.ok(adminService.litarTodosAdmin(activo));
    }
}
