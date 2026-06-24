package com.example.loginauth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Datos de respuesta del usuario autenticado o registrado")
public class UsuarioResponseDTO {
    
    @Schema(example = "1")
    private Long id;
    @Schema(example = "Juan Perez")
    private String nombre;
    @Schema(example = "12345678-9")
    private String rut;
    @Schema(example = "jperez")
    private String username;
    @Schema(example = "CAJERO")
    private String nombreRol;
    @Schema(description = "Token JWT generado", example = "eyJhbGciOiJIUzI1NiJ9...")
    private String token;
}
