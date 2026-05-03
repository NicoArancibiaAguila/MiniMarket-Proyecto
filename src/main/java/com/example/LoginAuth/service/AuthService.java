package com.example.loginauth.service;


import java.util.Optional;
import org.springframework.stereotype.Service;

import com.example.loginauth.dto.LoginRequestDTO;
import com.example.loginauth.dto.RegistroRequestDTO;
import com.example.loginauth.dto.UsuarioResponseDTO;
import com.example.loginauth.model.Rol;
import com.example.loginauth.model.Usuario;
import com.example.loginauth.repository.RolRepository;
import com.example.loginauth.repository.UsuarioRepository;

@Service
public class AuthService {
    

    
    private final UsuarioRepository usuarioRepository;                //traer al repo, en vez de final se puede usar un @AutoWired y se elimina el constructor
    private final RolRepository rolRepository;

    public AuthService(UsuarioRepository usuarioRepository, RolRepository rolRepository){          //inyeccion de dependencias
        this.usuarioRepository = usuarioRepository;
        this.rolRepository = rolRepository;
    }

    // METODO DE LOGIN
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

    // METODO DE REGISTRO
    public UsuarioResponseDTO registrar(RegistroRequestDTO request){

        if (usuarioRepository.existsByRut(request.getRut())) {
            throw new RuntimeException("Error: Ya existe un empleado registardo con este RUT");
        }

        if (usuarioRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Error: El nombre de usuario ya está en uso");
        }

        // aqui se busca que el rol exista en la bd 
        Rol rolAsignado = rolRepository.findByNombreRol(request.getNombreRol())
            .orElseThrow(() -> new RuntimeException("Error: El rol especificado no existe"));

        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setNombre(request.getNombre());
        nuevoUsuario.setRut(request.getRut());
        nuevoUsuario.setUsername(request.getUsername());

        nuevoUsuario.setPassword(request.getPassword());
        nuevoUsuario.setRol(rolAsignado);

        Usuario usuarioGuardado = usuarioRepository.save(nuevoUsuario);

        return new UsuarioResponseDTO(
            usuarioGuardado.getId(),
            usuarioGuardado.getNombre(),
            usuarioGuardado.getRut(),
            usuarioGuardado.getUsername(),
            usuarioGuardado.getRol().getNombreRol()
        );
    }

}
