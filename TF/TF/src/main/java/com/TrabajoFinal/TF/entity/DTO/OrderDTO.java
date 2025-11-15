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
    private String address;
    private String phone;
    private String paymentMethod;
    private String notes;
    private Double total;
    private String status;

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
        dto.setAddress(order.getAddress());
        dto.setPhone(order.getPhone());
        dto.setPaymentMethod(order.getPaymentMethod());
        dto.setNotes(order.getNotes());
        dto.setTotal(order.getTotal());
        dto.setStatus(order.getStatus());
        return dto;
    }
}


