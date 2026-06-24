package com.example.loginauth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "Estructura de datos para registrar un nuevo usuario")
public class RegistroRequestDTO {
    
    @Schema(description = "Nombre completo", example = "Juan Perez")
    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @Schema(description = "RUT del usuario", example = "12345678-9")
    @NotBlank(message = "El RUT es obligatorio")
    private String rut;

    @Schema(description = "Nombre de usuario para el sistema", example = "jperez")
    @NotBlank(message = "El nombre de usuario es obligatorio")
    @Size(min = 4, message = "El usuario debe tener al menos 4 caracteres")
    private String username;

    @Schema(description = "Contraseña de acceso", example = "secreta123")
    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres")
    private String password;

    @Schema(description = "Rol asignado al usuario", example = "CAJERO")
    @NotBlank(message = "El rol es obligatorio (ej: CAJERO, ADMIN)")
    private String nombreRol;

}
