package com.example.loginauth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegistroRequestDTO {
    
    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotBlank(message = "El RUT es obligatorio")
    private String rut;

    @NotBlank(message = "El nombre de usuario es obligatorio")
    @Size(min = 4, message = "El usuario debe tener al menos 4 caracteres")
    private String username;

    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres")
    private String password;

    @NotBlank(message = "El rol es obligatorio (ej: ROLE_CAJERO, ROLE_ADMIN)")
    private String nombreRol;

}
