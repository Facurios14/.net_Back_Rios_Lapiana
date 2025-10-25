package com.TrabajoFinal.TF.entity.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductoDto {
    private Long id;
    private String nombre;
    private double precio;
    private Long categoriaId;
}
