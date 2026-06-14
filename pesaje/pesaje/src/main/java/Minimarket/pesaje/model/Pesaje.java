package Minimarket.pesaje.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "pesajes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pesaje {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long productoId;

    @Column(nullable = false)
    private Double peso;

    private String unidadMedida; // Ej: "KG", "G"

    private LocalDateTime fechaPesaje;

    @PrePersist
    protected void onCreate() {
        this.fechaPesaje = LocalDateTime.now();
    }
}