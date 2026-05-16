package com.example.catalogoproductos.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.catalogoproductos.dto.ProductoRequestDTO;
import com.example.catalogoproductos.dto.ProductoResponseDTO;
import com.example.catalogoproductos.service.ProductoService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/productos")
@RequiredArgsConstructor
public class ProductoController {

    private final ProductoService productoService;

    // crear un producto: POST en localhost:8082/api/productos
    @PostMapping
    public ResponseEntity<ProductoResponseDTO> crear(@Valid @RequestBody ProductoRequestDTO request) {
        return ResponseEntity.ok(productoService.crearProducto(request));
    }

    // listar todos: GET en localhost:8082/api/productos
    @GetMapping
    public ResponseEntity<List<ProductoResponseDTO>> listarTodos() {
        return ResponseEntity.ok(productoService.obtenerTodosActivos());
    }

    // buscar por SKU: GET en localhost:8082/api/productos/{sku}
    @GetMapping("/{sku}")
    public ResponseEntity<ProductoResponseDTO> obtenerPorSku(@PathVariable String sku) {
        return ResponseEntity.ok(productoService.obtenerPorSku(sku));
    }

    // desactivar: DELETE en localhost:8082/api/productos/{sku}
    @DeleteMapping("/{sku}")
    public ResponseEntity<Void> desactivar(@PathVariable String sku) {
        productoService.desactivarProducto(sku);
        return ResponseEntity.noContent().build();
    }
}
