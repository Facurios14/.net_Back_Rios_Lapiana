package com.TrabajoFinal.TF.entity.DTO;


import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@NoArgsConstructor
public class UserRegisterDto {
    private String nombre;
    private String apellido;
    private String mail;
    private int celular;
    private String contrasena;

}
