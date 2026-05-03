package com.example.loginauth.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.loginauth.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    
    Optional<Usuario> findByUsername(String username);

    boolean existsByRut(String rut);

    boolean existsByUsername(String username);
}
