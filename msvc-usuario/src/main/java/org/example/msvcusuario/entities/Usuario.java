package org.example.msvcusuario.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table (name="usuario")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nombre", nullable = false)
    private String nombre;
    @Column(name = "apellido", nullable = false)
    private String apellido;
    @Column(name="numeroCelular", unique = true,nullable = false)
    private String numeroCelular;
    @Column(name = "email", unique = true, nullable = false)
    private String email;

}
