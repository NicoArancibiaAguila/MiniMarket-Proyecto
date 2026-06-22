package Minimarket.ventas.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import java.util.List;

@Data
public class VentaRequestDTO {

    @NotEmpty(message = "La venta debe tener al menos un producto")
    @Valid // Dispara la validación interna de la lista
    private List<DetalleVentaRequestDTO> detalles;
    
}