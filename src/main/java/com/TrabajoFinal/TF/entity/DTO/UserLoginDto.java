package com.TrabajoFinal.TF.entity.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserLoginDto {
    private String mail;
    private String contrasena;
}
