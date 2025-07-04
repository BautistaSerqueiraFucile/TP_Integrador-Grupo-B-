package org.example.msvcusuario.services;

import org.example.msvcusuario.entities.Usuario;
import org.example.msvcusuario.exceptions.UsuarioNoEncontradoException;
import org.example.msvcusuario.repositories.UsuarioRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.example.msvcusuario.entities.RolUsuario;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Servicio que encapsula la lógica de negocio para la gestión de Usuarios.
 */
@Service
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }


    /**
     * Obtiene todos los usuarios.
     * @return Una lista de todos los usuarios.
     */
    @Transactional(readOnly=true)
    public List<Usuario> listar() {
        return usuarioRepository.findAll();
    }

    /**
     * Busca un usuario por su ID.
     * @param id El ID del usuario a buscar.
     * @return El usuario encontrado.
     * @throws UsuarioNoEncontradoException si no se encuentra un usuario con ese ID.
     */
    @Transactional(readOnly = true)
    public Usuario buscarPorId(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioNoEncontradoException("Usuario no encontrado con ID: " + id));
    }


    /**
     * Crea un nuevo usuario, encriptando su contraseña.
     * @param usuario El usuario a crear.
     * @return El usuario guardado con su ID asignado.
     */
    @Transactional
    public Usuario crear(Usuario usuario) {
        String passwordEncriptada = passwordEncoder.encode(usuario.getPassword());
        usuario.setPassword(passwordEncriptada);
        return usuarioRepository.save(usuario);
    }

    /**
     * Actualiza los datos de un usuario existente.
     * Este métdo sigue el patrón "buscar, modificar y guardar".
     *
     * @param id El ID del usuario a actualizar.
     * @param usuarioDetails Un objeto Usuario con los nuevos datos.
     * @return El usuario actualizado y guardado en la base de datos.
     * @throws UsuarioNoEncontradoException si no se encuentra un usuario con el ID proporcionado.
     */
    @Transactional
    public Usuario actualizar(Long id, Usuario usuarioDetails) {
        this.buscarPorId(id);

        usuarioRepository.actualizarPerfil(
                id,
                usuarioDetails.getNombre(),
                usuarioDetails.getApellido(),
                usuarioDetails.getEmail(),
                usuarioDetails.getNumeroCelular(),
                usuarioDetails.getLatitud(),
                usuarioDetails.getLongitud()
        );

        return this.buscarPorId(id);
    }
    /**
     * Elimina un usuario por su ID.
     * @param id El ID del usuario a eliminar.
     */
    @Transactional
    public void eliminar(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new UsuarioNoEncontradoException("No se pudo eliminar. Usuario no encontrado con ID: " + id);
        }
        usuarioRepository.deleteById(id);
    }

    /**
     * Actualiza únicamente el rol de un usuario específico.
     *
     * @param id El ID del usuario cuyo rol se va a cambiar.
     * @param nuevoRol El nuevo rol a asignar.
     * @return El usuario con el rol actualizado.
     * @throws UsuarioNoEncontradoException si no se encuentra un usuario con el ID proporcionado.
     */
    @Transactional
    public Usuario actualizarRol(Long id, RolUsuario nuevoRol) {
        Usuario usuario = this.buscarPorId(id);
        usuarioRepository.actualizarRol(id, nuevoRol);
        usuario.setRol(nuevoRol);
        return usuario;
    }
}
