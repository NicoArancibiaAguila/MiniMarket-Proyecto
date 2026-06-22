package Minimarket.ventas.dto;

import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class DetalleVentaRequestDTO {
    
    // No usamos @NotNull en ambos porque un detalle puede ser UN abarrote (productoId + cantidad) 
    // o UN pan (solo pesajeId, donde el pesaje ya sabe qué producto es).
    
    private Long productoId; 
    
    @Min(value = 1, message = "Si envías cantidad, debe ser al menos 1")
    private Integer cantidad;

    // Si es panadería, solo nos envían el ticket del peso
    private Long pesajeId;
}