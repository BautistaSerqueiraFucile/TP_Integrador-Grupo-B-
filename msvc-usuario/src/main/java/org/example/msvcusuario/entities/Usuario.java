package org.example.msvcusuario.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table (name="usuario")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @Size(max = 50)
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @NotEmpty
    @Size(max = 50)
    @Column(name = "apellido", nullable = false)
    private String apellido;

    @NotEmpty
    @Column(name="numeroCelular", unique = true,nullable = false)
    private String numeroCelular;

    @NotEmpty
    @Email
    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @NotEmpty
    @Size(min = 6)
    @Column(name= "password", nullable = false)
    private String password;

    @NotNull
    @Column(name = "rol", nullable = false)
    @Enumerated(EnumType.STRING)
    private RolUsuario rol;

    @NotNull
    @Column(name = "latitud")
    private Double latitud;

    @NotNull
    @Column(name = "longitud")
    private Double longitud;
}
