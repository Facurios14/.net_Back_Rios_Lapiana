package com.TrabajoFinal.TF.entity.mapper;

import com.TrabajoFinal.TF.entity.Categoria;
import com.TrabajoFinal.TF.entity.DTO.CategoriaDto;

import java.util.List;
import java.util.stream.Collectors;

public class CategoriaMapper {
    private CategoriaMapper(){

    }
    public static CategoriaDto toDto(Categoria categoria){
        CategoriaDto dto=new CategoriaDto();
        dto.setId(categoria.getId());
        dto.setNombre(categoria.getNombre());
        return dto;
    }
    public static Categoria toEntity(CategoriaDto dto){
        Categoria categoria =new Categoria();
        categoria.setNombre(dto.getNombre());
        return categoria;
    }
    public static List<CategoriaDto> toDtoList(List<Categoria> categorias) {
        return categorias.stream().map(CategoriaMapper::toDto).collect(Collectors.toList());
    }
}
