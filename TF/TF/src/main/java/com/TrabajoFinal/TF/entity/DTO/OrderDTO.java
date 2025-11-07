package com.TrabajoFinal.TF.entity.DTO;

import com.TrabajoFinal.TF.entity.Order;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {

    private Long id;
    private Long userId;
    private LocalDateTime createdAt;
    private List<OrderItemDTO> items;

    public static OrderDTO fromEntity(Order order) {
        OrderDTO dto = new OrderDTO();

        dto.setId(order.getId());
        dto.setUserId(order.getUser().getId());
        dto.setCreatedAt(order.getCreatedAt());
        dto.setItems(
                order.getItems()
                        .stream()
                        .map(OrderItemDTO::fromEntity)
                        .toList()
        );

        return dto;
    }
}


