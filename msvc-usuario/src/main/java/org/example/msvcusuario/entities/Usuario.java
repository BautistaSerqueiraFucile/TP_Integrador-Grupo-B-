package org.example.msvcusuario.entities;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "ENTIDAD USUARIO")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único del usuario", example = "123", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @NotEmpty
    @Size(max = 50)
    @Column(name = "nombre", nullable = false)
    @Schema(description = "Nombre del usuario", example = "Juan", required = true)
    private String nombre;

    @NotEmpty
    @Size(max = 50)
    @Column(name = "apellido", nullable = false)
    @Schema(description = "Apellido del usuario", example = "Pérez", required = true)
    private String apellido;

    @NotEmpty
    @Column(name="numeroCelular", unique = true,nullable = false)
    @Schema(description = "Número de celular del usuario (único)", example = "1234567890", required = true)
    private String numeroCelular;

    @NotEmpty
    @Email
    @Column(name = "email", unique = true, nullable = false)
    @Schema(description = "Correo electrónico del usuario (único)", example = "juan.perez@example.com", required = true)
    private String email;

    @NotEmpty
    @Size(min = 6)
    @Column(name= "password", nullable = false)
    @Schema(description = "Contraseña del usuario (mínimo 6 caracteres)", example = "password123", required = true, accessMode = Schema.AccessMode.WRITE_ONLY)
    private String password;

    @NotNull
    @Column(name = "rol", nullable = false)
    @Enumerated(EnumType.STRING)
    @Schema(description = "Rol del usuario", example = "USUARIO", allowableValues = {"ADMIN", "USUARIO"}, required = true)
    private RolUsuario rol;

    @NotNull
    @Column(name = "latitud")
    @Schema(description = "Latitud de la ubicación del usuario", example = "-34.6037")
    private Double latitud;

    @NotNull
    @Column(name = "longitud")
    @Schema(description = "Longitud de la ubicación del usuario", example = "-58.3816")
    private Double longitud;
}
