package com.example.catalogoproductos.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import com.example.catalogoproductos.dto.ProductoRequestDTO;
import com.example.catalogoproductos.dto.ProductoResponseDTO;
import com.example.catalogoproductos.model.Producto;
import com.example.catalogoproductos.repository.ProductoRepository; // Asegúrate que esta ruta sea exacta
import com.example.catalogoproductos.service.ProductoService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor // Esto inyecta el Repository automáticamente (Lombok)
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository productoRepository;

    @Override
    public ProductoResponseDTO crearProducto(ProductoRequestDTO request) {

        //aca se valida si el SKU ya existe (tiene que ser igual al de inventario si no gg)
        if (productoRepository.existsBySku(request.getSku())) {
            throw new RuntimeException("Error: Ya existe un producto con el SKU: " + request.getSku());
        }

        // mapear de DTO a entidad
        Producto producto = new Producto();
        producto.setSku(request.getSku());
        producto.setNombre(request.getNombre());
        producto.setPrecio(request.getPrecio());
        producto.setDescripcion(request.getDescripcion());
        producto.setCategoria(request.getCategoria());

        // aca se guarda en la bd
        Producto guardado = productoRepository.save(producto);

        // y se retorna
        return mapearADTO(guardado);
    }

    @Override
    public List<ProductoResponseDTO> obtenerTodosActivos() {
        return productoRepository.findByActivoTrue().stream()
                .map(this::mapearADTO)
                .collect(Collectors.toList());
    }

    @Override
    public ProductoResponseDTO obtenerPorSku(String sku) {
        Producto producto = productoRepository.findBySku(sku)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con SKU: " + sku));
        return mapearADTO(producto);
    }

    @Override
    public void desactivarProducto(String sku) {
        Producto producto = productoRepository.findBySku(sku)
                .orElseThrow(() -> new RuntimeException("No se puede desactivar un SKU inexistente"));
        producto.setActivo(false);
        productoRepository.save(producto);
    }

    // metodo auxiliar pra poder transformar entidad -> dto
    private ProductoResponseDTO mapearADTO(Producto p) {
        ProductoResponseDTO dto = new ProductoResponseDTO();
        dto.setSku(p.getSku());
        dto.setNombre(p.getNombre());
        dto.setPrecio(p.getPrecio());
        dto.setDescripcion(p.getDescripcion());
        dto.setCategoria(p.getCategoria());
        dto.setActivo(p.getActivo());
        return dto;
    }
}
