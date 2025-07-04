package org.example.msvcusuario.repositories;

import org.example.msvcusuario.entities.RolUsuario;
import org.example.msvcusuario.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

/**
 * Repositorio para el acceso a datos de la entidad Usuario.
 * Proporciona los métodos CRUD básicos a través de JpaRepository y queries personalizadas.
 */
public interface UsuarioRepository extends JpaRepository<Usuario,Long> {
    /**
     * Actualiza el rol de un usuario específico directamente en la base de datos.
     * La anotación @Modifying es necesaria para indicar que esta query modifica datos.
     *
     * @param id El ID del usuario a modificar.
     * @param nuevoRol El nuevo RolUsuario a asignar.
     */
    @Modifying(clearAutomatically = true)
    @Query("UPDATE Usuario u SET u.rol = :nuevoRol WHERE u.id = :id")
    void actualizarRol(@Param("id") Long id, @Param("nuevoRol") RolUsuario nuevoRol);

    /**
     * Actualiza los datos del perfil de un usuario específico directamente en la base de datos.
     * No actualiza la contraseña ni el rol.
     *
     * @param id El ID del usuario a actualizar.
     * @param nombre El nuevo nombre del usuario.
     * @param apellido El nuevo apellido del usuario.
     * @param email El nuevo email del usuario.
     * @param numeroCelular El nuevo número de celular del usuario.
     * @param latitud La nueva latitud del usuario.
     * @param longitud La nueva longitud del usuario.
     */
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
