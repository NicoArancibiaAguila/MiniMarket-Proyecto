package Minimarket.pesaje.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class PesajeRequestDTO {

    @NotNull(message = "El ID del producto es obligatorio")
    private Long productoId;

    @NotNull(message = "El peso no puede ser nulo")
    @Positive(message = "El peso debe ser un valor mayor a cero")
    private Double peso; // Se recibirá el peso en gramos (ej: 180.0)

    @NotBlank(message = "El tipo de pan es obligatorio (BATIDO, HALLULLA, COLISA)")
    private String tipoPan;
}