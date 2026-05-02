package com.example.LoginAuth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.LoginAuth.dto.LoginRequestDTO;
import com.example.LoginAuth.dto.RegistroRequestDTO;
import com.example.LoginAuth.dto.UsuarioResponseDTO;
import com.example.LoginAuth.service.AuthService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    
    private AuthService authService;

    public AuthController(AuthService authService){
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<UsuarioResponseDTO> login(@Valid @RequestBody LoginRequestDTO request){

        UsuarioResponseDTO response = authService.login(request);

        return ResponseEntity.ok(response);

    }

    @PostMapping("/register")
    public ResponseEntity<UsuarioResponseDTO> registrar(@Valid @RequestBody RegistroRequestDTO request){

        UsuarioResponseDTO response = authService.registrar(request);

        return ResponseEntity.status(201).body(response);

    }

}
