package Minimarket.ventas.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
@Schema(description = "Detalle de un ítem en la venta. IMPORTANTE: Enviar 'productoId' y 'cantidad' para abarrotes, O enviar solo 'pesajeId' para panadería.")
public class DetalleVentaRequestDTO {
    
    @Schema(description = "ID del producto (usar solo para productos normales)", example = "10")
    private Long productoId; 
    
    @Min(value = 1, message = "Si envías cantidad, debe ser al menos 1")
    @Schema(description = "Cantidad del producto a comprar", example = "2")
    private Integer cantidad;

    @Schema(description = "ID del ticket de pesaje (usar solo si es pan. Ignorar productoId y cantidad)", example = "5")
    private Long pesajeId;
}