package Minimarket.ventas.client;

import Minimarket.ventas.dto.StockResponseDTO;
import Minimarket.ventas.dto.StockRequestDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "inventario-service")
public interface InventarioClient {

    @GetMapping("/api/inventario/{sku}")
    StockResponseDTO obtenerStock(@RequestHeader("Authorization") String token, @PathVariable("sku") String sku);

    @PutMapping("/api/inventario/disminuir")
    void disminuirStock(@RequestHeader("Authorization") String token, @RequestBody StockRequestDTO request);
}