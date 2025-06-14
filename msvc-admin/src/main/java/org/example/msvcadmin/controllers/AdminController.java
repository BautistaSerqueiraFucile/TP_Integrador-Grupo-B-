package org.example.msvcadmin.controllers;

import org.example.msvcadmin.services.AdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")

public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService){
        this.adminService = adminService;
    }


    @PutMapping("/cuentas/{cuentaId}/bloquear")
    public ResponseEntity<String> bloquearCuenta(@PathVariable Long cuentaId) {
        boolean resultado = adminService.bloquearCuenta(cuentaId);
        if (resultado) {
            return ResponseEntity.ok("Cuenta bloqueada exitosamente.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                    .body("No se pudo bloquear la cuenta.");
        }
    }



}
