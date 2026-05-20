package com.example.catalogoproductos.dto;

import lombok.Data;

@Data
public class InventarioInitDTO {
    private String sku;
    private Double stockActual;
    private Double nivelCritico;
    private Double nivelMaximo;
}