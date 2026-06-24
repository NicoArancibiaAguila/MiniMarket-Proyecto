package com.example.catalogoproductos.config;

import com.example.catalogoproductos.model.Producto;
import com.example.catalogoproductos.repository.ProductoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initCatalog(ProductoRepository productoRepository) {
        return args -> {

            if (productoRepository.count() > 0) {
                System.out.println("Catálogo ya tiene datos, omitiendo inicialización.");
                return;
            }

            System.out.println("Catálogo vacío. Cargando productos base...");

            crearProductoSiNoExiste(productoRepository, "PAN-001", "Pan Batido", "Pan batido crujiente, recién sacado del horno por Messi", 1800, "Panadería");
            crearProductoSiNoExiste(productoRepository, "PAN-002", "Pan Hallulla", "Hallullas tradicionales especiales hechas por CR7", 1800, "Panadería");
            crearProductoSiNoExiste(productoRepository, "PAN-003", "Pan Colisa", "Colisas preparadas por Alexis", 850, "Panadería");

            crearProductoSiNoExiste(productoRepository, "LAC-001", "Leche Entera Colun 1L", "Leche toda la magia del sur", 1100, "Abarrotes");
            crearProductoSiNoExiste(productoRepository, "BEB-001", "Bebida Coca Cola 2.5L", "Bebida gaseosa refrescante", 2400, "Abarrotes");
            crearProductoSiNoExiste(productoRepository, "ABA-001", "Aceite Vegetal 1L", "Aceite para cocinar multiuso", 1990, "Abarrotes");
            crearProductoSiNoExiste(productoRepository, "ABA-002", "Arroz Grado 1 - 1kg", "Arroz largo ancho de primera selección", 1350, "Abarrotes");
            crearProductoSiNoExiste(productoRepository, "ABA-003", "Café Instantáneo 100g", "Café granulado de tostado intenso", 3200, "Abarrotes");

            System.out.println("Catálogo inicializado con éxito con 8 productos.");
        };
    }

    private void crearProductoSiNoExiste(ProductoRepository repository, String sku, String nombre, String descripcion, int precio, String categoria) {
        if (repository.existsBySku(sku)) return;
        Producto nuevo = new Producto();
        nuevo.setSku(sku);
        nuevo.setNombre(nombre);
        nuevo.setDescripcion(descripcion);
        nuevo.setPrecio(precio);
        nuevo.setCategoria(categoria);
        repository.save(nuevo);
    }
}
