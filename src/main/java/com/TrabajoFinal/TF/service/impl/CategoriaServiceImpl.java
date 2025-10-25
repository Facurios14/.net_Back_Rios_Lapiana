package com.TrabajoFinal.TF.service.impl;

import com.TrabajoFinal.TF.entity.Categoria;
import com.TrabajoFinal.TF.entity.DTO.CategoriaDto;
import com.TrabajoFinal.TF.repository.CategoriaRepository;
import com.TrabajoFinal.TF.service.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaServiceImpl implements CategoriaService {
    private CategoriaRepository categoriaRepository;
    @Autowired
    public CategoriaServiceImpl(CategoriaRepository categoriaRepository){
        this.categoriaRepository=categoriaRepository;
    }
    @Override
    public Categoria save(CategoriaDto dto){
        Categoria categoria=new Categoria();
        categoria.setNombre(dto.getNombre());
        return categoriaRepository.save(categoria);
    }
    @Override
    public Categoria update(Long id, CategoriaDto dto) {
        return categoriaRepository.findById(id).map(categoria -> {
            categoria.setNombre(dto.getNombre());
            return categoriaRepository.save(categoria);
        }).orElseThrow(() -> new RuntimeException("Categoría no encontrada con ID: " + id));
    }
    @Override
    public List<Categoria> findAll(){
        return categoriaRepository.findAll();
    }
    @Override
    public Optional<Categoria> findById(Long id){
        return categoriaRepository.findById(id);
    }

    @Override
    public void deleteById(Long id){
        categoriaRepository.deleteById(id);
    }
}
