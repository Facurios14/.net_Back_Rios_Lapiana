package com.TrabajoFinal.TF.service;

import com.TrabajoFinal.TF.entity.Categoria;
import com.TrabajoFinal.TF.entity.DTO.CategoriaDto;
import com.TrabajoFinal.TF.entity.Producto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface CategoriaService {
    Categoria save(CategoriaDto dto);
    List<Categoria> findAll();
    Optional<Categoria> findById(Long Id);
    Categoria update(Long id,CategoriaDto dto);
    void deleteById(Long id);
}
