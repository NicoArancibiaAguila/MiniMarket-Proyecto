package minimarket.produccion.controller;



import minimarket.produccion.dto.request.ProduccionRequestDTO;
import minimarket.produccion.dto.response.ProduccionResponseDTO;
import minimarket.produccion.service.ProduccionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/produccion")
public class ProduccionController {

    @Autowired
    private ProduccionService service;

    // Crear lote de producción
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProduccionResponseDTO crear(
            @RequestBody ProduccionRequestDTO dto) {

        return service.crearLote(dto);
    }

    // Obtener todos los lotes
    @GetMapping
    public List<ProduccionResponseDTO> listar() {
        return service.obtenerTodos();
    }

    // Buscar lote por ID
    @GetMapping("/{id}")
    public ProduccionResponseDTO buscarPorId(
            @PathVariable Long id) {

        return service.buscarPorId(id);
    }

    // Eliminar lote
    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        service.eliminar(id);
    }
}