package com.example.viajesmascotas.repository;

import com.example.viajesmascotas.model.Usuario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UsuarioRepositoryTest {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    @DisplayName("Debe guardar y recuperar un usuario correctamente")
    @Timeout(5)
    public void testGuardarYRecuperarUsuario() {
        // Crear un nuevo usuario
        Usuario usuario = new Usuario();
        usuario.setNombre("Jessica González");
        usuario.setEmail("jessica@example.com");
        usuario.setPassword("secreto123");
        usuario.setRol("dueño de mascota");

        // Guardar el usuario usando TestEntityManager
        Usuario usuarioGuardado = entityManager.persistAndFlush(usuario);

        // Verificar que el usuario guardado tenga un ID asignado
        assertNotNull(usuarioGuardado.getId(), "El ID del usuario no debe ser nulo");

        // Recuperar el usuario por su ID
        Usuario usuarioRecuperado = usuarioRepository.findById(usuarioGuardado.getId()).orElse(null);

        // Verificar que el usuario recuperado no sea nulo y que los valores sean correctos
        assertNotNull(usuarioRecuperado, "El usuario recuperado no debe ser nulo");
        assertEquals("Jessica González", usuarioRecuperado.getNombre(), "El nombre es incorrecto");
        assertEquals("jessica@example.com", usuarioRecuperado.getEmail(), "El email es incorrecto");
        assertEquals("secreto123", usuarioRecuperado.getPassword(), "La contraseña es incorrecta");
        assertEquals("dueño de mascota", usuarioRecuperado.getRol(), "El rol es incorrecto");
    }

    @Test
    @DisplayName("No debe encontrar un usuario con ID inexistente")
    @Timeout(3)
    public void testNoEncontrarUsuarioPorId() {
        // Intentar recuperar un usuario con un ID que no existe
        Optional<Usuario> usuarioRecuperado = usuarioRepository.findById(99);

        // Verificar que no se haya encontrado el usuario
        assertTrue(usuarioRecuperado.isEmpty(), "El usuario no ha sido encontrado");
    }
}
