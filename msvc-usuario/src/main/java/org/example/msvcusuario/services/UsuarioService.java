package org.example.msvcusuario.services;

import org.example.msvcusuario.entities.Usuario;
import org.example.msvcusuario.exceptions.UsuarioNoEncontradoException;
import org.example.msvcusuario.repositories.UsuarioRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.example.msvcusuario.entities.RolUsuario;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
        String passwordEncriptada = passwordEncoder.encode(usuario.getPassword());
        usuario.setPassword(passwordEncriptada);
        return usuarioRepository.save(usuario);
    }

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
