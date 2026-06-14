package Minimarket.ventas.controller; // CORREGIDO: Nombre de paquete adaptado

import Minimarket.ventas.entity.Venta; // CORREGIDO: Importa la clase Venta correcta
import Minimarket.ventas.repository.VentaRepository; // CORREGIDO: Importa el repositorio correcto
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Habilita la clase para recibir peticiones HTTP de red y responder en formato JSON
@RestController
// URL base para usar este controlador (ej: http://localhost:8082/api/ventas)
@RequestMapping("/api/ventas")
public class VentaController {

    // Conecta de forma automática el repositorio mapeado para interactuar con la BD
    @Autowired
    private VentaRepository ventaRepository;

    // Endpoint GET: Retorna la lista completa de ventas guardadas
    @GetMapping
    public List<Venta> listarTodas() {
        return ventaRepository.findAll();
    }

    // Endpoint POST: Toma el JSON enviado, lo transforma en objeto Venta y lo registra en la BD
    @PostMapping
    public ResponseEntity<Venta> registrarVenta(@RequestBody Venta venta) {
        Venta nuevaVenta = ventaRepository.save(venta);
        return ResponseEntity.ok(nuevaVenta);
    }
}