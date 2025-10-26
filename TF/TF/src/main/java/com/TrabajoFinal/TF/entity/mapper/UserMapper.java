/*
package com.TrabajoFinal.TF.entity.mapper;

import com.TrabajoFinal.TF.entity.DTO.UserResponseDto;

import java.util.List;
import java.util.stream.Collectors;

public class UserMapper {
    private UserMapper(){

    }
    public static UserResponseDto toResponseDto(Usuario usuario){
        UserResponseDto dto = new UserResponseDto();
        dto.setId(usuario.getId());
        dto.setNombre(usuario.getNombre());
        dto.setApellido(usuario.getApellido());
        dto.setMail(usuario.getMail());
        dto.setCelular(usuario.getCelular());
        dto.setRol(usuario.getRol());
        return dto;
    }
    public static List<UserResponseDto> toResponseDtoList(List<Usuario> usuarios){
        return usuarios.stream()
                .map(UserMapper::toResponseDto)
                .collect(Collectors.toList());
    }
}
*/
