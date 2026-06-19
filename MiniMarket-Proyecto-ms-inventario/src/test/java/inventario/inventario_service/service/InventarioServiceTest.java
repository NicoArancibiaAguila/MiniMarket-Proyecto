package inventario.inventario_service.service;

import inventario.inventario_service.dto.request.StockRequestDTO;
import inventario.inventario_service.dto.response.StockResponseDTO;
import inventario.inventario_service.exception.ResourceNotFoundException;
import inventario.inventario_service.model.Inventario;
import inventario.inventario_service.repository.InventarioRepository;
import inventario.inventario_service.service.impl.InventarioServiceImpl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InventarioServiceTest {

    @Mock
    private InventarioRepository repository;

    @InjectMocks
    private InventarioServiceImpl service;

    @Test
    @DisplayName("obtenerPorSku debe retornar producto cuando existe")
    void obtenerPorSkuDebeRetornarProductoCuandoExiste() {

        Inventario producto = new Inventario();
        producto.setSku("PAN-001");
        producto.setStockActual(100.0);
        producto.setNivelCritico(10.0);
        producto.setNivelMaximo(500.0);

        when(repository.findBySku("PAN-001"))
                .thenReturn(Optional.of(producto));

        StockResponseDTO resultado =
                service.obtenerPorSku("PAN-001");

        assertNotNull(resultado," El producto retornado es nulo");
        assertEquals("PAN-001", resultado.getSku());

        verify(repository).findBySku("PAN-001");
    }

    @Test
    @DisplayName("obtenerPorSku debe lanzar excepción cuando no existe")
    void obtenerPorSkuDebeLanzarExcepcionCuandoNoExiste() {

        when(repository.findBySku("XXX"))
                .thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> service.obtenerPorSku("XXX"));

        verify(repository).findBySku("XXX");
    }

    @Test
    @DisplayName("obtenerTodos debe retornar lista de productos")
    void obtenerTodosDebeRetornarListaDeProductos() {

        Inventario producto1 = new Inventario();
        producto1.setSku("PAN-001");
        producto1.setStockActual(100.0);
        producto1.setNivelCritico(10.0);
        producto1.setNivelMaximo(500.0);

        Inventario producto2 = new Inventario();
        producto2.setSku("PAN-002");
        producto2.setStockActual(50.0);
        producto2.setNivelCritico(5.0);
        producto2.setNivelMaximo(300.0);

        when(repository.findAll())
                .thenReturn(List.of(producto1, producto2));

        List<StockResponseDTO> resultado =
                service.obtenerTodos();

        assertEquals(2, resultado.size());

        verify(repository).findAll();
    }

    @Test
    @DisplayName("crearProducto debe guardar producto")
    void crearProductoDebeGuardarProducto() {

        StockRequestDTO request =
                new StockRequestDTO();

        request.setSku("PAN-001");
        request.setStockActual(100.0);
        request.setNivelCritico(10.0);
        request.setNivelMaximo(500.0);

        when(repository.existsBySku("PAN-001"))
                .thenReturn(false);

        service.crearProducto(request);

        verify(repository).save(any(Inventario.class));
    }
}