package Minimarket.pesaje.service;

import Minimarket.pesaje.dto.PesajeRequestDTO;
import Minimarket.pesaje.dto.PesajeResponseDTO;
import Minimarket.pesaje.exception.BadRequestException;
import Minimarket.pesaje.model.Pesaje;
import Minimarket.pesaje.repository.PesajeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor // Genera automáticamente el constructor para los atributos 'final'
public class PesajeService {

    private final PesajeRepository pesajeRepository;
    
    // Constante con el precio establecido del kilo de pan
    private static final double PRECIO_KILO_PAN = 2500.0;

    public PesajeResponseDTO guardarPesaje(PesajeRequestDTO requestDTO) {
        String tipoPanInput = requestDTO.getTipoPan().toUpperCase();

        // Validamos que el tipo de pan pertenezca a los tres permitidos
        if (!tipoPanInput.equals("BATIDO") && !tipoPanInput.equals("HALLULLA") && !tipoPanInput.equals("COLISA")) {
            throw new BadRequestException("Tipo de pan no válido. Tipos permitidos: BATIDO, HALLULLA, COLISA");
        }

        // Lógica de cálculo: (Gramos / 1000) * $2500. Ejemplo: (180 / 1000) * 2500 = 450
        double precioCalculado = (requestDTO.getPeso() / 1000.0) * PRECIO_KILO_PAN;

        // Mapeo manual de DTO a Entidad
        Pesaje pesaje = new Pesaje();
        pesaje.setProductoId(requestDTO.getProductoId());
        pesaje.setPeso(requestDTO.getPeso());
        pesaje.setTipoPan(tipoPanInput);
        pesaje.setPrecioCalculado(precioCalculado);

        Pesaje pesajeGuardado = pesajeRepository.save(pesaje);

        // Retornamos el DTO de respuesta mapeado
        return convertToResponseDTO(pesajeGuardado);
    }

    public List<PesajeResponseDTO> obtenerTodos() {
        return pesajeRepository.findAll().stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    // Método auxiliar para transformar Entidad a Response DTO
    private PesajeResponseDTO convertToResponseDTO(Pesaje pesaje) {
        PesajeResponseDTO responseDTO = new PesajeResponseDTO();
        responseDTO.setId(pesaje.getId());
        responseDTO.setProductoId(pesaje.getProductoId());
        responseDTO.setPeso(pesaje.getPeso());
        responseDTO.setTipoPan(pesaje.getTipoPan());
        responseDTO.setPrecioCalculado(pesaje.getPrecioCalculado());
        responseDTO.setFechaPesaje(pesaje.getFechaPesaje());
        return responseDTO;
    }

    public PesajeResponseDTO obtenerPorId(Long id) {
        // Buscamos el pesaje, si no existe lanzamos un error básico
        Pesaje pesaje = pesajeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ticket de pesaje no encontrado"));
        
        // Lo mapeamos al DTO de respuesta que ya tenías creado
        PesajeResponseDTO dto = new PesajeResponseDTO();
        dto.setId(pesaje.getId());
        dto.setProductoId(pesaje.getProductoId());
        dto.setPeso(pesaje.getPeso());
        dto.setTipoPan(pesaje.getTipoPan());
        dto.setPrecioCalculado(pesaje.getPrecioCalculado());
        dto.setFechaPesaje(pesaje.getFechaPesaje());
        
        return dto;
    }
}