package com.TrabajoFinal.TF.entity.DTO;

import com.TrabajoFinal.TF.Estado;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
public class PedidoResponseDto {
    private Long id;
    private LocalDate fecha;
    private Estado estado;
    private Double total;
    private String usuarioMail;
    private List<DetallePedidoResponseDto> detalles;
}
