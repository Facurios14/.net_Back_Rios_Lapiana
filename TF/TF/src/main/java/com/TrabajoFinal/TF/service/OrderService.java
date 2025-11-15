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

     // Recibe el DTO completo desde el modal del frontend.

    public OrderDTO createOrder(OrderDTO orderDTO) {

        //  Obtener usuario
        User user = userRepository.findById(orderDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Crear el pedido y SETEAR LOS DATOS DEL CHECKOUT
        Order order = new Order();
        order.setUser(user);
        order.setCreatedAt(LocalDateTime.now());
        order.setAddress(orderDTO.getAddress());
        order.setPhone(orderDTO.getPhone());
        order.setPaymentMethod(orderDTO.getPaymentMethod());
        order.setNotes(orderDTO.getNotes());
        order.setStatus("PENDIENTE"); // Estado inicial

        // Guardamos el pedido para obtener un ID
        order = orderRepository.save(order);

        List<OrderItem> orderItemList = new ArrayList<>();
        double totalPedido = 0.0;

        // Procesar cada item del pedido
        for (OrderItemDTO itemDTO : orderDTO.getItems()) {

            // El frontend envía productId, no id
            Long productId = itemDTO.getProductId();
            if (productId == null) {
                throw new RuntimeException("El item del carrito no tiene productId");
            }

            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + productId));

            // Validar stock
            if (product.getStock() < itemDTO.getQuantity()) {
                throw new RuntimeException("Stock insuficiente para el producto: " + product.getName());
            }

            // Descontar stock
            product.setStock(product.getStock() - itemDTO.getQuantity());

            //  Actualizar estado en base al stock
            if (product.getStock() <= 0) {
                product.setStock(0);
                product.setStatus("NO_DISPONIBLE");
            }

            productRepository.save(product);

            //Crear el item del pedido
            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(product);
            orderItem.setOrder(order);
            orderItem.setPrice(product.getPrice());
            orderItem.setQuantity(itemDTO.getQuantity());
            orderItem = orderItemRepository.save(orderItem);
            orderItemList.add(orderItem);

            totalPedido += (product.getPrice() * itemDTO.getQuantity());
        }

        // total y la lista de items al pedido
        order.setTotal(totalPedido);
        order.setItems(orderItemList);

        // Volvemos a guardar el pedido para actualizar el total
        Order savedOrder = orderRepository.save(order);

        // Devolvemos el DTO completo
        return OrderDTO.fromEntity(savedOrder);
    }

    public List<OrderDTO> getOrdersByUser(Long userId) {
        return orderRepository.findByUserId(userId)
                .stream()
                .map(OrderDTO::fromEntity)
                .toList();
    }
    //parte admin
    public List<OrderDTO> getAllOrders() {
        return orderRepository.findAll()
                .stream()
                .map(OrderDTO::fromEntity)
                .toList();
    }

    public OrderDTO updateOrderStatus(Long orderId, String newStatus) {
        // Busca el pedido
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado con ID: " + orderId));

        order.setStatus(newStatus);

        Order updatedOrder = orderRepository.save(order);
        return OrderDTO.fromEntity(updatedOrder);
    }

}


