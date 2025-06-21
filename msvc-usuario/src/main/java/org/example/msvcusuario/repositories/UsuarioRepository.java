package org.example.msvcusuario.repositories;

import org.example.msvcusuario.entities.RolUsuario;
import org.example.msvcusuario.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;


public interface UsuarioRepository extends JpaRepository<Usuario,Long> {
    @Modifying(clearAutomatically = true)
    @Query("UPDATE Usuario u SET u.rol = :nuevoRol WHERE u.id = :id")
    void actualizarRol(@Param("id") Long id, @Param("nuevoRol") RolUsuario nuevoRol);
    @Modifying(clearAutomatically = true)
    @Query("UPDATE Usuario u SET u.nombre = :nombre, u.apellido = :apellido, u.email = :email, u.numeroCelular = :numeroCelular, u.latitud = :latitud, u.longitud = :longitud WHERE u.id = :id")
    void actualizarPerfil(
            @Param("id") Long id,
            @Param("nombre") String nombre,
            @Param("apellido") String apellido,
            @Param("email") String email,
            @Param("numeroCelular") String numeroCelular,
            @Param("latitud") Double latitud,
            @Param("longitud") Double longitud
    );
}
