package com.example.loginauth.service;


import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.loginauth.dto.LoginRequestDTO;
import com.example.loginauth.dto.RegistroRequestDTO;
import com.example.loginauth.dto.UsuarioResponseDTO;
import com.example.loginauth.model.Rol;
import com.example.loginauth.model.Usuario;
import com.example.loginauth.repository.RolRepository;
import com.example.loginauth.repository.UsuarioRepository;
import com.example.loginauth.security.jwt.JwtUtil;

@Service
public class AuthService {
    

    
    private final UsuarioRepository usuarioRepository;                //traer al repo, en vez de final se puede usar un @AutoWired y se elimina el constructor
    private final RolRepository rolRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;


    public AuthService(UsuarioRepository usuarioRepository, RolRepository rolRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil){          //inyeccion de dependencias
        this.usuarioRepository = usuarioRepository;
        this.rolRepository = rolRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
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
        if (!passwordEncoder.matches(request.getPassword(), usuario.getPassword())) {
            throw new RuntimeException("Error: Usuario o contraseña incorrectos");
        }

        //se genera el token
        String tokenGenerado = jwtUtil.generateToken(usuario.getUsername(), usuario.getRol().getNombreRol());

        //si todo pasa y esta bien, se retorna
        UsuarioResponseDTO response = new UsuarioResponseDTO();
        response.setId(usuario.getId());
        response.setNombre(usuario.getNombre());
        response.setRut(usuario.getRut());
        response.setUsername(usuario.getUsername());
        response.setNombreRol(usuario.getRol().getNombreRol());
        response.setToken(tokenGenerado);

        return response;
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

        //aca se encripta la clave antes de guardarla en bd
        nuevoUsuario.setPassword(passwordEncoder.encode(request.getPassword())); 
        nuevoUsuario.setRol(rolAsignado);

        Usuario usuarioGuardado = usuarioRepository.save(nuevoUsuario);

        //aca se registra el token va nulo, debe logearse para obtenerlo
        UsuarioResponseDTO response = new UsuarioResponseDTO();
        response.setId(usuarioGuardado.getId());
        response.setNombre(usuarioGuardado.getNombre());
        response.setRut(usuarioGuardado.getRut());
        response.setUsername(usuarioGuardado.getUsername());
        response.setNombreRol(usuarioGuardado.getRol().getNombreRol());
        
        return response;
    }

}
