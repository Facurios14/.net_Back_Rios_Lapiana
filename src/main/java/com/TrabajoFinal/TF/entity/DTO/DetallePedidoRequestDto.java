package com.TrabajoFinal.TF.entity.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DetallePedidoRequestDto {
    private Long productoId;
    private Integer cantidad;
}
