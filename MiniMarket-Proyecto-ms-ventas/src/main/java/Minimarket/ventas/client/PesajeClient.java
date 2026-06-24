package Minimarket.ventas.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import Minimarket.ventas.dto.PesajeDTO;

@FeignClient(name = "pesaje-service")
public interface PesajeClient {

    @GetMapping("/api/pesajes/{id}")
    PesajeDTO obtenerPesajePorId(@RequestHeader("Authorization") String token, @PathVariable("id") Long id);
}