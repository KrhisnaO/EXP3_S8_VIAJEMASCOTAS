package com.example.viajesmascotas.service;

import com.example.viajesmascotas.model.Usuario;
import com.example.viajesmascotas.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    private static final Logger logger = Logger.getLogger(UsuarioServiceImpl.class.getName());

    @Override
    public List<Usuario> getAllUsuarios() {
        logger.info("Obteniendo todos los usuarios...");
        return usuarioRepository.findAll();
    }

    @Override
    public Optional<Usuario> getUsuarioById(int id) {
        logger.info("Obteniendo usuario con ID: " + id);
        return usuarioRepository.findById(id);
    }

    @Override
    public Usuario createUsuario(Usuario usuario) {
        logger.info("Creando un nuevo usuario: " + usuario.getNombre());
        return usuarioRepository.save(usuario);
    }

    @Override
    public Usuario updateUsuario(int id, Usuario usuarioActualizado) {
        logger.info("Actualizando el usuario con ID: " + id);
        if (usuarioRepository.existsById(id)) {
            usuarioActualizado.setId(id); 
            return usuarioRepository.save(usuarioActualizado);
        } else {
            logger.warning("El usuario con ID " + id + " no existe.");
            return null;
        }
    }

    @Override
    public void deleteUsuario(int id) {
        logger.info("Eliminando usuario con ID: " + id);
        usuarioRepository.deleteById(id);
    }
}
