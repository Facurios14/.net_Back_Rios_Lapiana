package com.TrabajoFinal.TF.Controller;

import com.TrabajoFinal.TF.entity.DTO.OrderDTO;
import com.TrabajoFinal.TF.service.OrderService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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

    // --- ENDPOINT PARA ADMIN! ---
    @GetMapping
    public List<OrderDTO> getAllOrders() {
        return orderService.getAllOrders();
    }

    // ---ENDPOINT PARA ADMIN! ---
    @PutMapping("/{id}/status")
    public OrderDTO updateOrderStatus(@PathVariable Long id, @RequestBody Map<String, String> statusUpdate) {
        // Obtenemos el nuevo estado del cuerpo del JSON
        String newStatus = statusUpdate.get("status");
        if (newStatus == null) {
            throw new RuntimeException("El cuerpo debe contener la clave 'status'");
        }
        return orderService.updateOrderStatus(id, newStatus);
    }

}


