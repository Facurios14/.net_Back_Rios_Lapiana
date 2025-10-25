package com.TrabajoFinal.TF.entity.mapper;

import com.TrabajoFinal.TF.entity.DTO.ProductoDto;
import com.TrabajoFinal.TF.entity.Producto;

import java.util.List;
import java.util.stream.Collectors;

public class ProductoMapper {
    private ProductoMapper(){

    }
    public static ProductoDto toDto(Producto producto){
        ProductoDto dto=new ProductoDto();
        dto.setId(producto.getId());
        dto.setNombre(producto.getNombre());
        dto.setPrecio(producto.getPrecio());
        if (producto.getCategoria() != null){
            dto.setCategoriaId(producto.getCategoria().getId());
        }
        return dto;
    }
    public static List<ProductoDto> toDtoList(List<Producto> productos) {
        return productos.stream().map(ProductoMapper::toDto).collect(Collectors.toList());
    }
}
