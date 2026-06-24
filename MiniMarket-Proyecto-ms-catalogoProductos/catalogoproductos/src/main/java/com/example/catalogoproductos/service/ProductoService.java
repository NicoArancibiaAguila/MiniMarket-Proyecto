package com.example.catalogoproductos.service;

import java.util.List;
import com.example.catalogoproductos.dto.ProductoRequestDTO;
import com.example.catalogoproductos.dto.ProductoResponseDTO;

public interface ProductoService {
    // crear productos nuevos
    ProductoResponseDTO crearProducto(ProductoRequestDTO request);
    
    // ver todo el catalogo pero los activos no mas
    List<ProductoResponseDTO> obtenerTodosActivos();
    
    // Para que ventas/inventario nos pregunten por un SKU específico
    ProductoResponseDTO obtenerPorSku(String sku);

    ProductoResponseDTO obtenerPorId(Long id);
    
    // soft delete, se desactiva en lugar de borrar
    void desactivarProducto(String sku);
}
