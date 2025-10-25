package com.TrabajoFinal.TF.entity;

import com.TrabajoFinal.TF.Rol;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "users")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String apellido;
    private String mail;
    private int celular;
    private String contrasena;
    private Rol rol; // Rol rol

    // Relación: Un Usuario puede tener muchos Pedidos (1..m)
    // MappedBy indica el campo en la entidad Pedido que posee la clave foránea.
    @OneToMany(mappedBy = "usuario")
    private List<Pedido> pedidos;
}
