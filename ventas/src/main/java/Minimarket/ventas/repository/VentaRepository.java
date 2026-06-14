package Minimarket.ventas.repository; // CORREGIDO: Nombre de paquete adaptado

import Minimarket.ventas.entity.Venta; // CORREGIDO: Importa la entidad desde la ruta correcta
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// @Repository le indica a Spring que este componente se encargará del acceso a los datos
@Repository
// Al heredar de JpaRepository, ganamos gratis operaciones como .save(), .findAll() y .deleteById()
public interface VentaRepository extends JpaRepository<Venta, Long> {
}