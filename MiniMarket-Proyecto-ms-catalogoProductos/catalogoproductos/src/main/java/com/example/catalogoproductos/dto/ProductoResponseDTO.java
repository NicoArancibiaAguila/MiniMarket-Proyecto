package com.example.catalogoproductos.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Datos de respuesta del producto")
public class ProductoResponseDTO {
    @Schema(example = "1")
    private Long id;           // agregar este campo
    @Schema(example = "PAN-004")
    private String sku;
    @Schema(example = "Pan Amasado")
    private String nombre;
    @Schema(example = "Pan amasado casero horneado a leña")
    private String descripcion;
    @Schema(example = "2000")
    private Integer precio;
    @Schema(example = "Panadería")
    private String categoria;
    @Schema(description = "Estado actual en vitrina", example = "true")
    private Boolean activo;
}