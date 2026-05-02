package com.example.LoginAuth.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.LoginAuth.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    
    Optional<Usuario> findByUsername(String username);

    boolean existByRut(String rut);

    boolean existsByUsername(String username);
}
