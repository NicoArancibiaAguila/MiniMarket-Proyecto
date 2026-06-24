package Minimarket.pesaje.service;

import Minimarket.pesaje.dto.PesajeRequestDTO;
import Minimarket.pesaje.dto.PesajeResponseDTO;
import Minimarket.pesaje.exception.BadRequestException;
import Minimarket.pesaje.model.Pesaje;
import Minimarket.pesaje.repository.PesajeRepository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PesajeServiceTest {

    @Mock
    private PesajeRepository pesajeRepository;

    @InjectMocks
    private PesajeService pesajeService;

    @Test
    @DisplayName("guardarPesaje debe calcular el precio, guardar y retornar DTO")
    void guardarPesajeDebeCalcularPrecioYGuardar() {
        // Given
        PesajeRequestDTO request = new PesajeRequestDTO();
        request.setProductoId(1L);
        request.setPeso(500.0); // 500 gramos (medio kilo)
        request.setTipoPan("hallulla"); // Se envía en minúscula para probar el toUpperCase()

        Pesaje pesajeGuardado = new Pesaje();
        pesajeGuardado.setId(10L);
        pesajeGuardado.setProductoId(1L);
        pesajeGuardado.setPeso(500.0);
        pesajeGuardado.setTipoPan("HALLULLA");
        pesajeGuardado.setPrecioCalculado(1250.0); // (500 / 1000) * 2500
        pesajeGuardado.setFechaPesaje(LocalDateTime.now());

        when(pesajeRepository.save(any(Pesaje.class))).thenReturn(pesajeGuardado);

        // When
        PesajeResponseDTO resultado = pesajeService.guardarPesaje(request);

        // Then
        assertNotNull(resultado);
        assertEquals(10L, resultado.getId());
        assertEquals("HALLULLA", resultado.getTipoPan());
        assertEquals(1250.0, resultado.getPrecioCalculado()); // Verifica la lógica matemática

        verify(pesajeRepository).save(any(Pesaje.class));
    }

    @Test
    @DisplayName("guardarPesaje debe lanzar BadRequestException si el pan no es válido")
    void guardarPesajeDebeLanzarExcepcionConPanInvalido() {
        // Given
        PesajeRequestDTO request = new PesajeRequestDTO();
        request.setProductoId(1L);
        request.setPeso(1000.0);
        request.setTipoPan("MARRAQUETA"); // Tipo no permitido en tu lógica

        // When + Then
        BadRequestException exception = assertThrows(
                BadRequestException.class,
                () -> pesajeService.guardarPesaje(request)
        );

        assertTrue(exception.getMessage().contains("Tipo de pan no válido"));
        
        // Verifica que NUNCA se llame al repositorio para guardar si falla la validación
        verify(pesajeRepository, never()).save(any(Pesaje.class));
    }

    @Test
    @DisplayName("obtenerTodos debe retornar lista mapeada a DTOs")
    void obtenerTodosDebeRetornarLista() {
        // Given
        Pesaje p1 = new Pesaje(1L, 1L, 1000.0, "BATIDO", 2500.0, LocalDateTime.now());
        Pesaje p2 = new Pesaje(2L, 2L, 200.0, "COLISA", 500.0, LocalDateTime.now());

        when(pesajeRepository.findAll()).thenReturn(List.of(p1, p2));

        // When
        List<PesajeResponseDTO> resultado = pesajeService.obtenerTodos();

        // Then
        assertEquals(2, resultado.size());
        assertEquals("BATIDO", resultado.get(0).getTipoPan());
        assertEquals(2500.0, resultado.get(0).getPrecioCalculado());
        assertEquals("COLISA", resultado.get(1).getTipoPan());
        
        verify(pesajeRepository).findAll();
    }

    @Test
    @DisplayName("obtenerPorId debe retornar el pesaje mapeado cuando existe")
    void obtenerPorIdDebeRetornarPesajeCuandoExiste() {
        // Given
        Pesaje pesaje = new Pesaje(5L, 3L, 1000.0, "BATIDO", 2500.0, LocalDateTime.now());
        when(pesajeRepository.findById(5L)).thenReturn(Optional.of(pesaje));

        // When
        PesajeResponseDTO resultado = pesajeService.obtenerPorId(5L);

        // Then
        assertNotNull(resultado);
        assertEquals(5L, resultado.getId());
        assertEquals("BATIDO", resultado.getTipoPan());
        
        verify(pesajeRepository).findById(5L);
    }

    @Test
    @DisplayName("obtenerPorId debe lanzar RuntimeException cuando no existe")
    void obtenerPorIdDebeLanzarExcepcionCuandoNoExiste() {
        // Given
        when(pesajeRepository.findById(99L)).thenReturn(Optional.empty());

        // When + Then
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> pesajeService.obtenerPorId(99L)
        );

        assertEquals("Ticket de pesaje no encontrado", exception.getMessage());
        verify(pesajeRepository).findById(99L);
    }
}