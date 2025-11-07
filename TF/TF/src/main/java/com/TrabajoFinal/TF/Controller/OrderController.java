package com.TrabajoFinal.TF.Controller;

import com.TrabajoFinal.TF.entity.DTO.OrderDTO;
import com.TrabajoFinal.TF.service.OrderService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "http://localhost:5173")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public OrderDTO createOrder(@RequestBody OrderDTO dto) {
        return orderService.createOrder(dto);
    }

    @GetMapping("/user/{id}")
    public List<OrderDTO> getOrdersByUser(@PathVariable Long id) {
        return orderService.getOrdersByUser(id);
    }
}


