package com.example.catalogoproductos.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import com.example.catalogoproductos.controller.ProductoControllerV2;
import com.example.catalogoproductos.dto.ProductoResponseDTO;
import com.example.catalogoproductos.model.Producto;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class ProductoModelAssembler implements RepresentationModelAssembler<Producto, EntityModel<ProductoResponseDTO>> {

    @Override
    public EntityModel<ProductoResponseDTO> toModel(Producto producto) {
        ProductoResponseDTO dto = new ProductoResponseDTO();
        dto.setId(producto.getId()); 
        dto.setSku(producto.getSku());
        dto.setNombre(producto.getNombre());
        dto.setDescripcion(producto.getDescripcion());
        dto.setPrecio(producto.getPrecio());
        dto.setCategoria(producto.getCategoria());
        dto.setActivo(producto.getActivo());

        return EntityModel.of(dto,
            linkTo(methodOn(ProductoControllerV2.class).obtenerPorSku(producto.getSku())).withSelfRel(),
            linkTo(methodOn(ProductoControllerV2.class).listarTodos()).withRel("productos")
        );
    }
}