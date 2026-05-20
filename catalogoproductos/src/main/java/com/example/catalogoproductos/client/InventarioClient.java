package com.example.catalogoproductos.client;

import com.example.catalogoproductos.dto.ProductoRequestDTO;
import com.example.catalogoproductos.security.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        name = "inventario-service",
        url = "http://localhost:8084/",
        configuration = FeignConfig.class
)
public interface InventarioClient {

@PostMapping("/api/inventario/crear")
Object crearInventario(
        @RequestBody ProductoRequestDTO dto
);
}