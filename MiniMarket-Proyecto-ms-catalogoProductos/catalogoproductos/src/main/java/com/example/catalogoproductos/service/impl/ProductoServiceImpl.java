package com.example.catalogoproductos.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import com.example.catalogoproductos.dto.ProductoRequestDTO;
import com.example.catalogoproductos.dto.ProductoResponseDTO;
import com.example.catalogoproductos.dto.InventarioInitDTO; // Asegúrate de este import
import com.example.catalogoproductos.model.Producto;
import com.example.catalogoproductos.repository.ProductoRepository;
import com.example.catalogoproductos.service.ProductoService;
import com.example.catalogoproductos.client.InventarioClient;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository productoRepository;
    private final InventarioClient inventarioClient;

    @Override
    public ProductoResponseDTO crearProducto(ProductoRequestDTO request) {

        // 1. Validar si el SKU ya existe en Catálogo
        if (productoRepository.existsBySku(request.getSku())) {
            throw new RuntimeException("Error: Ya existe un producto con el SKU: " + request.getSku());
        }

        // 2. Mapear de DTO a entidad Producto
        Producto producto = new Producto();
        producto.setSku(request.getSku());
        producto.setNombre(request.getNombre());
        producto.setPrecio(request.getPrecio());
        producto.setDescripcion(request.getDescripcion());
        producto.setCategoria(request.getCategoria());
        producto.setActivo(true); // Se inicializa como activo

        // 3. Guardar en la BD de Catálogo
        Producto guardado = productoRepository.save(producto);

        // 4. Traducción: Preparar el objeto que Inventario espera
        InventarioInitDTO invDto = new InventarioInitDTO();
        invDto.setSku(guardado.getSku());
        invDto.setStockActual(0.0);
        invDto.setNivelCritico(10.0);
        invDto.setNivelMaximo(100.0);

        // 5. Llamar a Inventario vía Feign (el Interceptor inyectará el token automáticamente)
        inventarioClient.crearInventario(invDto);

        // 6. Retornar la respuesta
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

    @Override
    public ProductoResponseDTO obtenerPorId(Long id) {
        Producto producto = productoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + id));
        return mapearADTO(producto);
    }

    // Método auxiliar para transformar entidad -> dto
    private ProductoResponseDTO mapearADTO(Producto p) {
        ProductoResponseDTO dto = new ProductoResponseDTO();
        dto.setId(p.getId()); 
        dto.setSku(p.getSku());
        dto.setNombre(p.getNombre());
        dto.setPrecio(p.getPrecio());
        dto.setDescripcion(p.getDescripcion());
        dto.setCategoria(p.getCategoria());
        dto.setActivo(p.getActivo());
        return dto;
    }
}
