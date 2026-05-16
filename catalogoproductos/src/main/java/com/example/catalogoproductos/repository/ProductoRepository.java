package com.example.catalogoproductos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.catalogoproductos.model.Producto;
import java.util.Optional;
import java.util.List;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

    // buscar por SKU 
    Optional<Producto> findBySku(String sku);

    // buscar solo los productos marcados como activos (como la vitrina)
    List<Producto> findByActivoTrue();

    // verificar si un SKU ya existe antes de crear (para no duplicar)
    boolean existsBySku(String sku);
}