package com.TrabajoFinal.TF.repository;

import com.TrabajoFinal.TF.entity.DetallePedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DetalleRepository extends JpaRepository<DetallePedido,Long> {
}
