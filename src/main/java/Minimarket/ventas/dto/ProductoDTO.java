package Minimarket.ventas.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter 
public class ProductoDTO {
    private Long id;
    private String sku;
    private String nombre;
    private Integer precio;
    private String categoria;
    private Boolean activo;
}