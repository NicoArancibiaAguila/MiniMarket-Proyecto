package com.example.catalogoproductos.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class ProductoRequestDTO {

    @NotBlank(message = "El SKU es obligatorio")
    private String sku;

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    private String descripcion; 

    @NotNull(message = "El precio es obligatorio")
    @Min(value = 0, message = "El precio no puede ser negativo")
    private Integer precio;

    private String categoria; 
}
