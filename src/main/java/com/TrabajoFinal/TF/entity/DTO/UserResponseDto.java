package com.TrabajoFinal.TF.entity.DTO;

import com.TrabajoFinal.TF.Rol;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor

public class UserResponseDto {

    private Long id;
    private String nombre;
    private String apellido;
    private String mail;
    private int celular;
    private Rol rol;
}
