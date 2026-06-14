package Minimarket.pesaje.controller;

import Minimarket.pesaje.model.Pesaje;
import Minimarket.pesaje.service.PesajeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pesajes")
public class PesajeController {

    @Autowired
    private PesajeService pesajeService;

    @PostMapping
    public ResponseEntity<Pesaje> crearPesaje(@RequestBody Pesaje pesaje) {
        Pesaje nuevoPesaje = pesajeService.guardarPesaje(pesaje);
        return new ResponseEntity<>(nuevoPesaje, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Pesaje>> listarPesajes() {
        return ResponseEntity.ok(pesajeService.obtenerTodos());
    }
}