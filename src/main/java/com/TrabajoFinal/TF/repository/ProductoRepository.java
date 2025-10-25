package com.TrabajoFinal.TF.repository;

import com.TrabajoFinal.TF.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository<Producto,Long> {

}
