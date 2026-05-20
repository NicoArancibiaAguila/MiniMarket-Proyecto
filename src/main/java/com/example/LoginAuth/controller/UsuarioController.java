package com.example.loginauth.controller;

import com.example.loginauth.model.Usuario;
import com.example.loginauth.repository.UsuarioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioRepository usuarioRepository;

    public UsuarioController(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    // Listar los usuarios solo ADMIN y SUPERVISOR
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPERVISOR')")
    @GetMapping
    public ResponseEntity<List<Usuario>> listarUsuarios() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        return ResponseEntity.ok(usuarios);
    }

    // crear un usuario solo ADMIN
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public ResponseEntity<?> crearUsuario(@RequestBody Usuario usuario) {
        // Validaciones usando los métodos de tu repositorio
        if (usuarioRepository.existsByRut(usuario.getRut())) {
            return ResponseEntity.badRequest().body("Error: El RUT ya está registrado.");
        }
        if (usuarioRepository.existsByUsername(usuario.getUsername())) {
            return ResponseEntity.badRequest().body("Error: El Username ya está en uso.");
        }
        
        Usuario nuevoUsuario = usuarioRepository.save(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoUsuario);
    }

    // actualizar usuario solo ADMIN
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarUsuario(@PathVariable Long id, @RequestBody Usuario usuarioActualizado) {
        Optional<Usuario> usuarioExistente = usuarioRepository.findById(id);
        
        if (usuarioExistente.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: Usuario no encontrado.");
        }

        Usuario usuario = usuarioExistente.get();
        usuario.setNombre(usuarioActualizado.getNombre());
        // Nota: En un sistema real, si actualizas la password aquí, debes volver a encriptarla con BCrypt.
        
        usuarioRepository.save(usuario);
        return ResponseEntity.ok("Usuario actualizado con éxito.");
    }

    // eliminar solo ADMIN
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarUsuario(@PathVariable Long id) {
        if (!usuarioRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: Usuario no encontrado.");
        }
        
        usuarioRepository.deleteById(id);
        return ResponseEntity.ok("Usuario eliminado con éxito.");
    }
}
