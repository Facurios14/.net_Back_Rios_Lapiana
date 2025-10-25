package com.TrabajoFinal.TF.service;

import com.TrabajoFinal.TF.entity.DTO.ProductoDto;
import com.TrabajoFinal.TF.entity.Producto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public interface ProductoService {
    Producto save(ProductoDto dto);
    List<Producto> findAll();
    Optional<Producto> findById(Long Id);
    Producto update(Long id, ProductoDto dto);
    void deleteById(Long id);
}
