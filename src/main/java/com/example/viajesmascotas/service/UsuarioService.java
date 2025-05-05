package com.example.viajesmascotas.service;

import com.example.viajesmascotas.model.Usuario;

import java.util.List;
import java.util.Optional;

public interface UsuarioService {

    List<Usuario> getAllUsuarios();
    Optional<Usuario> getUsuarioById(int id);
    Usuario createUsuario(Usuario usuario);
    Usuario updateUsuario(int id, Usuario usuarioActualizado);
    void deleteUsuario(int id);
}
