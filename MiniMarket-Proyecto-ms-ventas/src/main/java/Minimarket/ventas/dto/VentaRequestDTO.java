package Minimarket.ventas.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import java.util.List;

@Data
@Schema(description = "Objeto que representa la solicitud para registrar una nueva venta en caja")
public class VentaRequestDTO {

    @NotEmpty(message = "La venta debe tener al menos un producto")
    @Valid // Dispara la validación interna de la lista
    @Schema(description = "Lista de detalles de la venta (productos normales o tickets de pan)")
    private List<DetalleVentaRequestDTO> detalles;
}