package com.example.viajesmascotas.controller;

import com.example.viajesmascotas.model.Usuario;
import com.example.viajesmascotas.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.CollectionModel;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    private static final Logger logger = Logger.getLogger(UsuarioController.class.getName());

    // Obtener todos los usuarios
    @GetMapping
    public CollectionModel<EntityModel<Usuario>> getAllUsuarios() {
        logger.info("GET /usuarios - Obteniendo todos los usuarios");
        List<Usuario> usuarios = usuarioService.getAllUsuarios();

        List<EntityModel<Usuario>> usuariosConLinks = usuarios.stream()
                .map(usuario -> EntityModel.of(usuario,
                        linkTo(methodOn(UsuarioController.class).getUsuarioById(usuario.getId())).withSelfRel(),
                        linkTo(methodOn(UsuarioController.class).getAllUsuarios()).withRel("usuarios")))
                .toList();

        return CollectionModel.of(usuariosConLinks,
                linkTo(methodOn(UsuarioController.class).getAllUsuarios()).withSelfRel());
    }

    // Obtener un usuario por ID
    @GetMapping("/{id}")
    public EntityModel<Usuario> getUsuarioById(@PathVariable int id) {
        logger.info("GET /usuarios/" + id + " - Obteniendo usuario por ID");
        Optional<Usuario> usuarioOpt = usuarioService.getUsuarioById(id);
        
        if (usuarioOpt.isEmpty()) {
            throw new UsuarioNotFoundException("Usuario con ID " + id + " no encontrado");
        }

        Usuario usuario = usuarioOpt.get();
        return EntityModel.of(usuario,
                linkTo(methodOn(UsuarioController.class).getUsuarioById(id)).withSelfRel(),
                linkTo(methodOn(UsuarioController.class).getAllUsuarios()).withRel("usuarios"));
    }

    // Crear un nuevo usuario
    @PostMapping
    public EntityModel<Usuario> createUsuario(@RequestBody Usuario usuario) {
        logger.info("POST /usuarios - Creando nuevo usuario");
        Usuario nuevoUsuario = usuarioService.createUsuario(usuario);

        return EntityModel.of(nuevoUsuario,
                linkTo(methodOn(UsuarioController.class).getUsuarioById(nuevoUsuario.getId())).withSelfRel(),
                linkTo(methodOn(UsuarioController.class).getAllUsuarios()).withRel("usuarios"));
    }

    // Actualiza un usuario existente
    @PutMapping("/{id}")
    public EntityModel<Usuario> updateUsuario(@PathVariable int id, @RequestBody Usuario usuario) {
        logger.info("PUT /usuarios/" + id + " - Actualizando usuario");
        Usuario actualizado = usuarioService.updateUsuario(id, usuario);

        if (actualizado == null) {
            throw new UsuarioNotFoundException("No se pudo actualizar. Usuario con ID " + id + " no encontrado.");
        }

        return EntityModel.of(actualizado,
                linkTo(methodOn(UsuarioController.class).getUsuarioById(id)).withSelfRel(),
                linkTo(methodOn(UsuarioController.class).getAllUsuarios()).withRel("usuarios"));
    }

    // Eliminar un usuario por ID
    @DeleteMapping("/{id}")
    public void deleteUsuario(@PathVariable int id) {
        logger.info("DELETE /usuarios/" + id + " - Eliminando usuario");
        usuarioService.deleteUsuario(id);
    }

    // EXCEPCIÓN personalizada 
    @ResponseStatus(code = org.springframework.http.HttpStatus.NOT_FOUND)
    static class UsuarioNotFoundException extends RuntimeException {
        public UsuarioNotFoundException(String message) {
            super(message);
        }
    }
}
