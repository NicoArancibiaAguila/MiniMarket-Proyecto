package minimarket.produccion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import minimarket.produccion.model.LoteProduccion;

import java.util.List;

@Repository
public interface ProduccionRepository extends JpaRepository<LoteProduccion, Long> {

    // Buscar por SKU del producto fabricado
    List<LoteProduccion> findByProductoSku(String productoSku);

}
