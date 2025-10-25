package com.TrabajoFinal.TF.entity.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
public class PedidoRequestDto {
    private Long usuarioId;
    private List <DetallePedidoRequestDto> detalles;
}
