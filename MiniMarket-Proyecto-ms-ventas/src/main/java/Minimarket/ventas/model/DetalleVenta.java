package Minimarket.ventas.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "detalles_venta")
@Data
public class DetalleVenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relación Muchos a 1: Muchos detalles pertenecen a una sola venta
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "venta_id", nullable = false)
    @JsonIgnore // Evita bucles infinitos al convertir a JSON
    private Venta venta;

    @Column(name = "producto_id", nullable = false)
    private Long productoId;

    // Si es abarrote, se usa cantidad. Si es pan, puede ser 1 o nulo, dependiendo de cómo lo pesemos.
    private Integer cantidad;

    // Si el producto es pan, guardamos el ID del ticket de pesaje
    @Column(name = "pesaje_id")
    private Long pesajeId;

    @Column(nullable = false)
    private Double precioUnitario;

    @Column(nullable = false)
    private Double subtotal;
}