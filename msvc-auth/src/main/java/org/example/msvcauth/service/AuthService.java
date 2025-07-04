package org.example.msvcauth.service;


import jakarta.transaction.Transactional;
import org.example.msvcauth.dto.LoginRequest;
import org.example.msvcauth.entities.AuthEntity;
import org.example.msvcauth.repository.AuthRepository;
import org.example.msvcauth.segurity.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
public class AuthService {
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthRepository authRepository;

    @Transactional
    public ResponseEntity<?> loguearUsuario(LoginRequest request){
        System.out.println("🟡 Intentando login para: " + request.getUsername());

        Optional<AuthEntity> usuario = authRepository.findByUsername(request.getUsername());

        if (usuario.isEmpty()) {
            System.out.println("🔴 Usuario no encontrado");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Collections.singletonMap("mensaje", "Usuario no encontrado"));
        }

        AuthEntity usuarioAuth = usuario.get();

        if (!passwordEncoder.matches(request.getPassword(), usuarioAuth.getPassword())) {
            System.out.println("🔴 Contraseña incorrecta para " + usuarioAuth.getUsername());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Collections.singletonMap("mensaje", "Contraseña incorrecta"));
        }

        String token = jwtUtil.generateToken(usuarioAuth.getUsername(), usuarioAuth.getRol());

        System.out.println("✅ Login exitoso para " + usuarioAuth.getUsername() + " - Rol: " + usuarioAuth.getRol());
        return ResponseEntity.ok(Collections.singletonMap("token", token));
    }

    @Transactional
    public ResponseEntity<?> registrar(AuthEntity usuario) {
        if (authRepository.findByUsername(usuario.getUsername()).isPresent()) {
            return ResponseEntity.badRequest()
                    .body(Collections.singletonMap("mensaje", "El usuario ya existe"));
        }

        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        return ResponseEntity.status(HttpStatus.CREATED).body(authRepository.save(usuario));
    }
}
