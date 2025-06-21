package org.example.msvcusuario.services;

import org.example.msvcusuario.entities.Usuario;
import org.example.msvcusuario.exceptions.UsuarioNoEncontradoException;
import org.example.msvcusuario.repositories.UsuarioRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.example.msvcusuario.entities.RolUsuario;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
@Service
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional(readOnly=true)
    public List<Usuario> listar() {
        return usuarioRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Usuario buscarPorId(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioNoEncontradoException("Usuario no encontrado con ID: " + id));
    }

    @Transactional
    public Usuario crear(Usuario usuario) {
        // Encriptamos la contraseña ANTES de guardarla.
        String passwordEncriptada = passwordEncoder.encode(usuario.getPassword());
        usuario.setPassword(passwordEncriptada);
        return usuarioRepository.save(usuario);
    }

    @Transactional
    public Usuario actualizar(Long id, Usuario usuarioDetails) {
        // 1. Buscamos el usuario para asegurarnos de que existe.
        this.buscarPorId(id);

        // 2. Ejecutamos la actualización directa en la base de datos.
        //    Esto evita completamente el problema de la validación de la contraseña.
        usuarioRepository.actualizarPerfil(
                id,
                usuarioDetails.getNombre(),
                usuarioDetails.getApellido(),
                usuarioDetails.getEmail(),
                usuarioDetails.getNumeroCelular(),
                usuarioDetails.getLatitud(),
                usuarioDetails.getLongitud()
        );

        // 3. Volvemos a buscar el usuario para devolver el objeto actualizado.
        return this.buscarPorId(id);
    }

    @Transactional
    public void eliminar(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new UsuarioNoEncontradoException("No se pudo eliminar. Usuario no encontrado con ID: " + id);
        }
        usuarioRepository.deleteById(id);
    }

    @Transactional
    public Usuario actualizarRol(Long id, RolUsuario nuevoRol) {
        Usuario usuario = this.buscarPorId(id);
        usuarioRepository.actualizarRol(id, nuevoRol);
        usuario.setRol(nuevoRol);
        return usuario;
    }
}
