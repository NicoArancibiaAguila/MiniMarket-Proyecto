package Minimarket.pesaje.dto;

import lombok.Data;
import org.springframework.hateoas.RepresentationModel;
import java.time.LocalDateTime;

@Data
public class PesajeResponseDTO extends RepresentationModel<PesajeResponseDTO> {
    private Long id;
    private Long productoId;
    private Double peso;
    private String tipoPan;
    private Double precioCalculado;
    private LocalDateTime fechaPesaje;
}