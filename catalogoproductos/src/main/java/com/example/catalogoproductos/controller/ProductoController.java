package com.example.catalogoproductos.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize; // 🔥 Importación de seguridad
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

    // CREAR: Solo ADMIN y SUPERVISOR
    @PreAuthorize("hasAnyAuthority('ADMIN', 'ROLE_ADMIN', 'SUPERVISOR', 'ROLE_SUPERVISOR')")
    @PostMapping
    public ResponseEntity<ProductoResponseDTO> crear(@Valid @RequestBody ProductoRequestDTO request) {
        return ResponseEntity.ok(productoService.crearProducto(request));
    }

    // LISTAR TODOS: Cualquier usuario autenticado (con token válido)
    @GetMapping
    public ResponseEntity<List<ProductoResponseDTO>> listarTodos() {
        return ResponseEntity.ok(productoService.obtenerTodosActivos());
    }

    // BUSCAR POR SKU: Cualquier usuario autenticado (con token válido)
    @GetMapping("/{sku}")
    public ResponseEntity<ProductoResponseDTO> obtenerPorSku(@PathVariable String sku) {
        return ResponseEntity.ok(productoService.obtenerPorSku(sku));
    }

    // DESACTIVAR (Eliminar lógico): Solo ADMIN y SUPERVISOR
    @PreAuthorize("hasAnyAuthority('ADMIN', 'ROLE_ADMIN', 'SUPERVISOR', 'ROLE_SUPERVISOR')")
    @DeleteMapping("/{sku}")
    public ResponseEntity<Void> desactivar(@PathVariable String sku) {
        productoService.desactivarProducto(sku);
        return ResponseEntity.noContent().build();
    }
}
