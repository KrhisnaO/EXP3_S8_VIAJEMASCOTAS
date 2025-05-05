package com.example.viajesmascotas.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;

import com.example.viajesmascotas.model.Usuario;
import com.example.viajesmascotas.repository.UsuarioRepository;

public class UsuarioServiceTest {

    @InjectMocks
    private UsuarioServiceImpl usuarioService;

    @Mock
    private UsuarioRepository usuarioRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    // Test para crear un usuariO
    public void testCreateUsuario() {
        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setNombre("Jessica González");
        nuevoUsuario.setEmail("jessica@example.com");
        nuevoUsuario.setPassword("secreto123");
        nuevoUsuario.setRol("dueño de mascota");

        Usuario usuarioEsperado = new Usuario();
        usuarioEsperado.setId(1);
        usuarioEsperado.setNombre("Jessica González");

        // Simular el guardado del usuario
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuarioEsperado);

        Usuario resultado = usuarioService.createUsuario(nuevoUsuario);

        assertEquals(usuarioEsperado.getNombre(), resultado.getNombre(), "El nombre debe coincidir");
        assertEquals(usuarioEsperado.getId(), resultado.getId(), "El ID debe coincidir");
    }

    @Test
    // Test para crear un usuario con error de integridad de datos
    public void testCreateUsuarioError() {
        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setNombre("Jessica González");
        nuevoUsuario.setEmail("jessica@example.com");
        nuevoUsuario.setPassword("secreto123");
        nuevoUsuario.setRol("dueño de mascota");

        // Simular una excepción de violación de integridad al guardar
        when(usuarioRepository.save(any(Usuario.class)))
            .thenThrow(new DataIntegrityViolationException("Violación de integridad de datos"));

        assertThrows(DataIntegrityViolationException.class, () -> {
            usuarioService.createUsuario(nuevoUsuario);
        });
    }

    @Test
    // Test para obtener un usuario por su ID
    public void testGetUsuarioById() {
        Usuario usuario = new Usuario();
        usuario.setId(1);
        usuario.setNombre("Jessica González");
        usuario.setEmail("jessica@example.com");
        usuario.setPassword("secreto123");
        usuario.setRol("dueño de mascota");

        // Simular la búsqueda del usuario por su ID
        when(usuarioRepository.findById(1)).thenReturn(Optional.of(usuario));

        
        // Verificar que el usuario fue encontrado y los datos son correctos
        Usuario usuarioEncontrado = usuarioService.getUsuarioById(1).get();

        assertEquals(usuario.getNombre(), usuarioEncontrado.getNombre(), "El nombre debe ser correcto");
        assertEquals(usuario.getEmail(), usuarioEncontrado.getEmail(), "El email debe ser correcto");
        assertEquals(usuario.getPassword(), usuarioEncontrado.getPassword(), "La contraseña debe ser correcta");
        assertEquals(usuario.getRol(), usuarioEncontrado.getRol(), "El rol debe ser correcto");
    }

}
