package Minimarket.ventas.dto;

import lombok.Data;

@Data
public class PesajeDTO {
    private Long id;
    private Long productoId;
    private Double precioCalculado;
    private String tipoPan;  // agregar este campo — pesaje lo devuelve
}