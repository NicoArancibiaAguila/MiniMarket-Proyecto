package com.example.LoginAuth.service;


import java.util.Optional;
import org.springframework.stereotype.Service;

import com.example.LoginAuth.dto.LoginRequestDTO;
import com.example.LoginAuth.dto.UsuarioResponseDTO;
import com.example.LoginAuth.model.Usuario;
import com.example.LoginAuth.repository.UsuarioRepository;

@Service
public class AuthService {
    

    
    private final UsuarioRepository usuarioRepository;                //traer al repo, en vez de final se puede usar un @AutoWired y se elimina el constructor

    public AuthService(UsuarioRepository usuarioRepository){          //inyeccion de dependencias
        this.usuarioRepository = usuarioRepository;
    }

    //logica negocio
    public UsuarioResponseDTO login(LoginRequestDTO request){

        //buscar si existe el username
        Optional<Usuario> usuarioOpt = usuarioRepository.findByUsername(request.getUsername());
        if (usuarioOpt.isEmpty()) {
            throw new RuntimeException("Error: Usuario o contraseña incorrectos");
            // tiene que ser usuario o contra incorrectos, nunca dar informacion de más, siempre generico
        }

        Usuario usuario = usuarioOpt.get();

        //comparacion de contraseñas
        if (!usuario.getPassword().equals(request.getPassword())) {
            throw new RuntimeException("Error: Usuario o contraseña incorrectos");
        }

        //si todo pasa y esta bien, se retorna
        return new UsuarioResponseDTO(
            usuario.getId(),
            usuario.getNombre(),
            usuario.getRut(),
            usuario.getUsername(),
            usuario.getRol().getNombreRol()
        );

    }

}
