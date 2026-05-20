package inventario.inventario_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import inventario.inventario_service.model.Inventario;

import java.util.Optional;
import java.util.List;

@Repository
public interface InventarioRepository extends JpaRepository<Inventario, Long> {

    // Buscar por SKU 
    Optional<Inventario> findBySku(String sku);

    //  Verificar si existe
    boolean existsBySku(String sku);

    //Obtiene productos en estado crítico según su propio nivelCritico
    @Query("SELECT i FROM Inventario i WHERE i.stockActual <= i.nivelCritico")
List<Inventario> findStockCritico();
}
