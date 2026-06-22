package Minimarket.ventas.controller;

import Minimarket.ventas.dto.VentaRequestDTO;
import Minimarket.ventas.model.Venta;
import Minimarket.ventas.service.VentaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ventas")
@RequiredArgsConstructor
public class VentaController {

    private final VentaService ventaService;

    @GetMapping
    public ResponseEntity<List<Venta>> listarTodas() {
        return ResponseEntity.ok(ventaService.listarTodas());
    }

    @PostMapping
    public ResponseEntity<Venta> registrarPedido(@Valid @RequestBody VentaRequestDTO ventaRequestDTO) {
        Venta nuevoPedido = ventaService.crearPedido(ventaRequestDTO);
        return new ResponseEntity<>(nuevoPedido, HttpStatus.CREATED);
    }

    @PutMapping("/{id}/confirmar")
    public ResponseEntity<Venta> confirmarPago(@PathVariable("id") Long id) {
        Venta ventaPagada = ventaService.confirmarPago(id);
        return ResponseEntity.ok(ventaPagada);
    }
}