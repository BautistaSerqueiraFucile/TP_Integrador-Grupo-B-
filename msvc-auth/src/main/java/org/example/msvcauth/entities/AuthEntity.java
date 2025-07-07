package org.example.msvcauth.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "autenticacion")
public class AuthEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String username;

    @Column
    private String password;

    @Column
    private String rol; // ADMIN o USER

}
