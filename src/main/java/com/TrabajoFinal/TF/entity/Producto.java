package com.TrabajoFinal.TF.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private Double precio;
    private Integer stock;
    @ManyToOne
    @JoinColumn
    private Categoria categoria;
    @OneToMany(mappedBy = "producto")
    private List<DetallePedido> detallesPedido;
}
