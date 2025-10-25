package com.TrabajoFinal.TF.entity.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DetallePedidoDto {
    private Long productoId;
    private int cantidad;
}
