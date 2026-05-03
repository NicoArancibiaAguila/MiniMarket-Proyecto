package com.example.loginauth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.loginauth.dto.LoginRequestDTO;
import com.example.loginauth.dto.RegistroRequestDTO;
import com.example.loginauth.dto.UsuarioResponseDTO;
import com.example.loginauth.service.AuthService;

import org.springframework.web.bind.annotation.RequestBody;
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
