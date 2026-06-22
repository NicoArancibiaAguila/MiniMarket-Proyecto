package Minimarket.ventas.service;

import Minimarket.ventas.client.*;
import Minimarket.ventas.dto.*;
import Minimarket.ventas.exception.*;
import Minimarket.ventas.model.*;
import Minimarket.ventas.repository.VentaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class VentaService {

    private final VentaRepository ventaRepository;
    private final CatalogoClient catalogoClient;
    private final InventarioClient inventarioClient;
    private final PesajeClient pesajeClient;

    // Mapa fijo: tipoPan → SKU del catálogo
    private static final Map<String, String> SKU_POR_TIPO_PAN = Map.of(
        "BATIDO",  "PAN-001",
        "HALLULLA", "PAN-002",
        "COLISA",  "PAN-003"
    );

    private String getToken() {
        try {
            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            return attr.getRequest().getHeader("Authorization");
        } catch (Exception e) { return ""; }
    }

    public List<Venta> listarTodas() { return ventaRepository.findAll(); }

    @Transactional
    public Venta crearPedido(VentaRequestDTO requestDTO) {
        String token = getToken();
        Venta venta = new Venta();
        venta.setEstado(Venta.EstadoVenta.PENDIENTE_PAGO);
        double totalVenta = 0.0;
        List<DetalleVenta> detalles = new ArrayList<>();

        for (DetalleVentaRequestDTO detalleDTO : requestDTO.getDetalles()) {
            DetalleVenta detalle = new DetalleVenta();
            detalle.setVenta(venta);

            if (detalleDTO.getPesajeId() != null) {
                // --- FLUJO PAN: usar tipoPan para obtener el SKU ---
                PesajeDTO pesaje = pesajeClient.obtenerPesajePorId(token, detalleDTO.getPesajeId());
                if (pesaje == null) throw new ResourceNotFoundException("Pesaje no encontrado con ID: " + detalleDTO.getPesajeId());

                // Obtener SKU desde el tipo de pan
                String tipoPan = pesaje.getTipoPan() != null ? pesaje.getTipoPan().toUpperCase() : "";
                String sku = SKU_POR_TIPO_PAN.get(tipoPan);
                if (sku == null) throw new ResourceNotFoundException("No se encontró SKU para el tipo de pan: " + tipoPan);

                // Verificar stock en inventario usando el SKU
                StockResponseDTO stock = inventarioClient.obtenerStock(token, sku);
                if (stock == null || stock.getStockActual() < 1)
                    throw new InsufficientStockException("Sin stock para: " + sku);

                detalle.setPesajeId(pesaje.getId());
                detalle.setProductoId(pesaje.getProductoId());
                detalle.setCantidad(1);
                detalle.setPrecioUnitario(pesaje.getPrecioCalculado());
                detalle.setSubtotal(pesaje.getPrecioCalculado());

            } else if (detalleDTO.getProductoId() != null) {
                // --- FLUJO ABARROTE: buscar por ID en catálogo ---
                ProductoDTO prod = catalogoClient.obtenerProductoPorId(token, detalleDTO.getProductoId());
                if (prod == null) throw new ResourceNotFoundException("Producto no encontrado con ID: " + detalleDTO.getProductoId());

                String sku = prod.getSku() != null ? prod.getSku() : String.valueOf(prod.getId());
                StockResponseDTO stock = inventarioClient.obtenerStock(token, sku);
                if (stock == null || stock.getStockActual() < detalleDTO.getCantidad())
                    throw new InsufficientStockException("Stock insuficiente para: " + prod.getNombre());

                detalle.setProductoId(prod.getId());
                detalle.setCantidad(detalleDTO.getCantidad());
                detalle.setPrecioUnitario(prod.getPrecio().doubleValue());
                detalle.setSubtotal(prod.getPrecio().doubleValue() * detalleDTO.getCantidad());
            }

            totalVenta += detalle.getSubtotal();
            detalles.add(detalle);
        }

        venta.setTotal(totalVenta);
        venta.setDetalles(detalles);
        return ventaRepository.save(venta);
    }

    @Transactional
    public Venta confirmarPago(Long ventaId) {
        String token = getToken();
        Venta venta = ventaRepository.findById(ventaId)
                .orElseThrow(() -> new ResourceNotFoundException("Venta no encontrada con ID: " + ventaId));

        for (DetalleVenta detalle : venta.getDetalles()) {

            String sku;
            if (detalle.getPesajeId() != null) {
                // Es pan — buscar SKU por productoId en catálogo
                ProductoDTO prod = catalogoClient.obtenerProductoPorId(token, detalle.getProductoId());
                sku = prod != null && prod.getSku() != null ? prod.getSku() : String.valueOf(detalle.getProductoId());
            } else {
                ProductoDTO prod = catalogoClient.obtenerProductoPorId(token, detalle.getProductoId());
                sku = prod != null && prod.getSku() != null ? prod.getSku() : String.valueOf(detalle.getProductoId());
            }

            StockRequestDTO request = new StockRequestDTO();
            request.setSku(sku);
            request.setStockActual((double) detalle.getCantidad());
            inventarioClient.disminuirStock(token, request);
        }

        venta.setEstado(Venta.EstadoVenta.PAGADA);
        return ventaRepository.save(venta);
    }
}