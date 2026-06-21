package com.example.loginauth.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import com.example.loginauth.controller.UsuarioControllerV2;
import com.example.loginauth.model.Usuario;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class UsuarioModelAssembler implements RepresentationModelAssembler<Usuario, EntityModel<Usuario>> {

    @Override
    public EntityModel<Usuario> toModel(Usuario usuario) {
        return EntityModel.of(usuario,
            linkTo(methodOn(UsuarioControllerV2.class).listarUsuarios()).withRel("usuarios"),
            linkTo(methodOn(UsuarioControllerV2.class).actualizarUsuario(usuario.getId(), null)).withRel("actualizar"),
            linkTo(methodOn(UsuarioControllerV2.class).eliminarUsuario(usuario.getId())).withRel("eliminar")
        );
    }
}
