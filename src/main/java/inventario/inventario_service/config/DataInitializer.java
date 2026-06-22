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

        if (inventarioRepository.count() > 0) {
            System.out.println("Inventario ya tiene datos, omitiendo inicialización.");
            return;
        }

        System.out.println("Inventario vacío. Cargando stock base...");

        crearInventario(inventarioRepository, "PAN-001", 50.0, 10.0, 200.0);
        crearInventario(inventarioRepository, "PAN-002", 40.0, 15.0, 150.0);
        crearInventario(inventarioRepository, "PAN-003", 5.0,  20.0, 100.0);

        crearInventario(inventarioRepository, "LAC-001", 30.0, 10.0, 100.0);
        crearInventario(inventarioRepository, "BEB-001", 60.0, 15.0, 150.0);
        crearInventario(inventarioRepository, "ABA-001", 25.0, 5.0,  50.0);
        crearInventario(inventarioRepository, "ABA-002", 80.0, 20.0, 300.0);
        crearInventario(inventarioRepository, "ABA-003", 12.0, 10.0, 50.0);

        System.out.println("Inventario inicializado con éxito con 8 registros sincronizados con el catálogo.");
    }

    private void crearInventario(InventarioRepository repo, String sku, Double stockActual, Double nivelCritico, Double nivelMaximo) {
        if (repo.existsBySku(sku)) return;
        Inventario inv = new Inventario();
        inv.setSku(sku);
        inv.setStockActual(stockActual);
        inv.setNivelCritico(nivelCritico);
        inv.setNivelMaximo(nivelMaximo);
        repo.save(inv);
    }
}
