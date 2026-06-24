package Minimarket.pesaje.controller;

import Minimarket.pesaje.assembler.PesajeModelAssembler;
import Minimarket.pesaje.dto.PesajeRequestDTO;
import Minimarket.pesaje.dto.PesajeResponseDTO;
import Minimarket.pesaje.service.PesajeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/pesajes")
@RequiredArgsConstructor
@Tag(name = "Pesaje de Productos", description = "Endpoints para el registro y consulta de pesajes.")
@SecurityRequirement(name = "bearerAuth")
public class PesajeController {

    private final PesajeService pesajeService;
    private final PesajeModelAssembler assembler;

    @Operation(summary = "Registrar un nuevo pesaje")
    @ApiResponse(responseCode = "201", description = "Pesaje creado correctamente")
    @PostMapping
    public ResponseEntity<PesajeResponseDTO> crearPesaje(@Valid @RequestBody PesajeRequestDTO pesajeRequestDTO) {
        PesajeResponseDTO nuevoPesaje = pesajeService.guardarPesaje(pesajeRequestDTO);
        return new ResponseEntity<>(assembler.toModel(nuevoPesaje), HttpStatus.CREATED);
    }

    @Operation(summary = "Listar todos los pesajes")
    @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente")
    @GetMapping
    public ResponseEntity<List<PesajeResponseDTO>> listarPesajes() {
        List<PesajeResponseDTO> pesajes = pesajeService.obtenerTodos().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return ResponseEntity.ok(pesajes);
    }

    @Operation(summary = "Buscar pesaje por ID")
    @ApiResponse(responseCode = "200", description = "Pesaje encontrado")
    @ApiResponse(responseCode = "404", description = "Pesaje no encontrado")
    @GetMapping("/{id}")
    public ResponseEntity<PesajeResponseDTO> obtenerPesajePorId(@PathVariable("id") Long id) {
        PesajeResponseDTO dto = pesajeService.obtenerPorId(id);
        return ResponseEntity.ok(assembler.toModel(dto));
    }
}