package Minimarket.ventas.entity; // CORREGIDO: Ahora coincide con tu carpeta base

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

// @Entity le avisa a Spring que esta clase representa una tabla en la base de datos
@Entity
// @Table define el nombre físico que tendrá la tabla en tu base de datos Oracle
@Table(name = "ventas")
// @Data genera automáticamente los Getters, Setters y toString en segundo plano
@Data 
public class Venta {

    // @Id define que esta variable será la llave primaria de la tabla
    @Id
    // Configura el ID para que sea autoincrementable de forma secuencial en la base de datos
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Almacena el ID del producto comprado (proveniente de tu catálogo)
    @Column(name = "producto_id", nullable = false)
    private Long productoId;

    // Guarda la cantidad exacta de unidades que se están llevando
    @Column(nullable = false)
    private Integer cantidad;

    // Almacena el valor total de la transacción financiera
    @Column(nullable = false)
    private Double total;

    // Registra la estampa de tiempo (fecha y hora) de la venta
    @Column(name = "fecha_venta")
    private LocalDateTime fechaVenta;

    // Antes de guardar en la BD, inyecta la hora actual del sistema automáticamente
    @PrePersist
    protected void onCreate() {
        this.fechaVenta = LocalDateTime.now();
    }
}