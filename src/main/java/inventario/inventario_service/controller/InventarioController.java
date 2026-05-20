package inventario.inventario_service.controller;

import inventario.inventario_service.dto.request.StockRequestDTO;
import inventario.inventario_service.dto.response.StockResponseDTO;
import inventario.inventario_service.service.InventarioService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize; 
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventario")
public class InventarioController {

    @Autowired
    private InventarioService service;

    // --- NUEVO ENDPOINT: Listar todo el inventario ---
    // Accesible para cualquier rol autenticado
    @GetMapping
    public ResponseEntity<List<StockResponseDTO>> listarTodo() {
        return ResponseEntity.ok(service.obtenerTodos());
    }

    // Crear producto: Solo ADMIN y SUPERVISOR
    @PreAuthorize("hasAnyAuthority('ADMIN', 'ROLE_ADMIN', 'SUPERVISOR', 'ROLE_SUPERVISOR')")
    @PostMapping("/crear")
    public ResponseEntity<StockResponseDTO> crearProducto(
            @Valid @RequestBody StockRequestDTO request) {
        return ResponseEntity.ok(service.crearProducto(request));
    }

    // Aumentar stock manual: Solo ADMIN y SUPERVISOR
    @PreAuthorize("hasAnyAuthority('ADMIN', 'ROLE_ADMIN', 'SUPERVISOR', 'ROLE_SUPERVISOR')")
    @PutMapping("/aumentar")
    public ResponseEntity<StockResponseDTO> aumentarStock(
            @Valid @RequestBody StockRequestDTO request) {
        return ResponseEntity.ok(service.aumentarStock(request));
    }

    // Disminuir stock manual: Solo ADMIN y SUPERVISOR
    @PreAuthorize("hasAnyAuthority('ADMIN', 'ROLE_ADMIN', 'SUPERVISOR', 'ROLE_SUPERVISOR')")
    @PutMapping("/disminuir")
    public ResponseEntity<StockResponseDTO> disminuirStock(
            @Valid @RequestBody StockRequestDTO request) {
        return ResponseEntity.ok(service.disminuirStock(request));
    }

    // Buscar por SKU: Cualquier rol autenticado (Cajero, Panadero, Admin, Supervisor)
    @GetMapping("/{sku}")
    public ResponseEntity<StockResponseDTO> obtenerPorSku(@PathVariable String sku) {
        return ResponseEntity.ok(service.obtenerPorSku(sku));
    }

    // Stock crítico: Cualquier rol autenticado necesita ver alertas
    @GetMapping("/critico")
    public ResponseEntity<List<StockResponseDTO>> obtenerStockCritico() {
        return ResponseEntity.ok(service.obtenerStockCritico());
    }

    // Sumar stock desde producción: Permitir a ADMIN, SUPERVISOR y al proceso automático
    @PreAuthorize("hasAnyAuthority('ADMIN', 'ROLE_ADMIN', 'SUPERVISOR', 'ROLE_SUPERVISOR', 'PRODUCCION')")
    @PutMapping("/sumar-stock/{sku}/{cantidad}")
    public ResponseEntity<String> sumarStock(
            @PathVariable String sku,
            @PathVariable int cantidad
    ) {
        return ResponseEntity.ok(service.sumarStock(sku, cantidad));
    }
}