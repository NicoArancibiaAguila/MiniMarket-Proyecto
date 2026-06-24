package Minimarket.pesaje.assembler;

import Minimarket.pesaje.controller.PesajeController;
import Minimarket.pesaje.dto.PesajeResponseDTO;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class PesajeModelAssembler extends RepresentationModelAssemblerSupport<PesajeResponseDTO, PesajeResponseDTO> {

    public PesajeModelAssembler() {
        super(PesajeController.class, PesajeResponseDTO.class);
    }

    @Override
    public PesajeResponseDTO toModel(PesajeResponseDTO dto) {
        dto.add(linkTo(methodOn(PesajeController.class).obtenerPesajePorId(dto.getId())).withSelfRel());
        dto.add(linkTo(methodOn(PesajeController.class).listarPesajes()).withRel("pesajes"));
        return dto;
    }
}