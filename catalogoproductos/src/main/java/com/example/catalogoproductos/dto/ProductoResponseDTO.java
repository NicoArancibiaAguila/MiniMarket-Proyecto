package com.example.catalogoproductos.dto;

import lombok.Data;

@Data
public class ProductoResponseDTO {
    private String sku;
    private String nombre;
    private String descripcion;
    private Integer precio;
    private String categoria;
    private Boolean activo;
}
