package inventario.inventario_service.config;

import inventario.inventario_service.model.Inventario;
import inventario.inventario_service.repository.InventarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final InventarioRepository inventarioRepository;

    @Override
    public void run(String... args) throws Exception {
        
        // reset, Borra el inventario completo
        inventarioRepository.deleteAll();
        System.out.println("Registros de inventario limpiados. Restaurando stock base...");

        //  Categoría: Panadería 
        // PAN-001: Stock normal (Pan Batido)
        crearInventario(inventarioRepository, "PAN-001", 50.0, 10.0, 200.0);
        
        // PAN-002: Stock normal (Pan Hallulla)
        crearInventario(inventarioRepository, "PAN-002", 40.0, 15.0, 150.0);
        
        // PAN-003:  STOCK CRÍTICO INTENCIONAL (Pan Colisa). Stock (5) es menor al crítico (20).
        crearInventario(inventarioRepository, "PAN-003", 5.0, 20.0, 100.0); 

        // Categoría: Abarrotes 
        crearInventario(inventarioRepository, "LAC-001", 30.0, 10.0, 100.0);
        crearInventario(inventarioRepository, "BEB-001", 60.0, 15.0, 150.0);
        crearInventario(inventarioRepository, "ABA-001", 25.0, 5.0, 50.0);
        crearInventario(inventarioRepository, "ABA-002", 80.0, 20.0, 300.0);
        crearInventario(inventarioRepository, "ABA-003", 12.0, 10.0, 50.0);

        System.out.println("Inventario inicializado con éxito sincronizado con los 8 productos del catálogo.");
    }

    // Metodo auxiliar para crear registros limpios
    private void crearInventario(InventarioRepository repo, String sku, Double stockActual, Double nivelCritico, Double nivelMaximo) {
        Inventario inv = new Inventario();
        inv.setSku(sku);
        inv.setStockActual(stockActual);
        inv.setNivelCritico(nivelCritico);
        inv.setNivelMaximo(nivelMaximo);
        
        // La fecha se genera automáticamente por @PrePersist en el modelo
        repo.save(inv);
    }
}
