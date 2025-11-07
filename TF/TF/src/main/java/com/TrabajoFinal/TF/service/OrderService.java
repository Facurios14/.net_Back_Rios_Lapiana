package com.TrabajoFinal.TF.service;

import com.TrabajoFinal.TF.entity.*;
import com.TrabajoFinal.TF.entity.DTO.OrderDTO;
import com.TrabajoFinal.TF.entity.DTO.OrderItemDTO;
import com.TrabajoFinal.TF.repository.OrderItemRepository;
import com.TrabajoFinal.TF.repository.OrderRepository;
import com.TrabajoFinal.TF.repository.ProductRepository;
import com.TrabajoFinal.TF.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public OrderService(
            OrderRepository orderRepository,
            OrderItemRepository orderItemRepository,
            ProductRepository productRepository,
            UserRepository userRepository
    ) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    public OrderDTO createOrder(OrderDTO orderDTO) {

        // 1️⃣ Obtener usuario
        User user = userRepository.findById(orderDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // 2️⃣ Crear el pedido
        Order order = new Order();
        order.setUser(user);
        order.setCreatedAt(LocalDateTime.now());

        order = orderRepository.save(order);

        List<OrderItem> orderItemList = new ArrayList<>();

        // 3️⃣ Procesar cada item del pedido
        for (OrderItemDTO itemDTO : orderDTO.getItems()) {

            Product product = productRepository.findById(itemDTO.getProductId())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

            // Validar stock
            if (product.getStock() < itemDTO.getQuantity()) {
                throw new RuntimeException("Stock insuficiente para el producto: " + product.getName());
            }

            // 4️⃣ Descontar stock
            product.setStock(product.getStock() - itemDTO.getQuantity());

            // 5️⃣ Actualizar estado en base al stock
            if (product.getStock() <= 0) {
                product.setStock(0);
                product.setStatus("NO_DISPONIBLE");
            } else {
                product.setStatus("DISPONIBLE");
            }

            productRepository.save(product);

            // 6️⃣ Crear el item del pedido
            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(product);
            orderItem.setOrder(order);
            orderItem.setPrice(product.getPrice());
            orderItem.setQuantity(itemDTO.getQuantity());

            orderItem = orderItemRepository.save(orderItem);

            orderItemList.add(orderItem);
        }

        // 7️⃣ Asociar items al pedido
        order.setItems(orderItemList);

        return OrderDTO.fromEntity(order);
    }

    public List<OrderDTO> getOrdersByUser(Long userId) {
        return orderRepository.findByUserId(userId)
                .stream()
                .map(OrderDTO::fromEntity)
                .toList();
    }
}


