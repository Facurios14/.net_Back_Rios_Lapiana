package com.TrabajoFinal.TF.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class DetallePedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int cantidad;
    private Double subtotal;
    @ManyToOne
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;
    @ManyToOne
    @JoinColumn(name = "producto_id")
    private Producto producto;
}
