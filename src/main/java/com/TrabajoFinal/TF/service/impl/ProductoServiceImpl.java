package com.TrabajoFinal.TF.service.impl;

import com.TrabajoFinal.TF.entity.Categoria;
import com.TrabajoFinal.TF.entity.DTO.ProductoDto;
import com.TrabajoFinal.TF.entity.Producto;
import com.TrabajoFinal.TF.repository.CategoriaRepository;
import com.TrabajoFinal.TF.repository.ProductoRepository;
import com.TrabajoFinal.TF.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoServiceImpl implements ProductoService {
    private ProductoRepository productoRepository;
    private CategoriaRepository categoriaRepository;
    @Autowired
    public ProductoServiceImpl(ProductoRepository productoRepository,CategoriaRepository categoriaRepository){
        this.productoRepository=productoRepository;
        this.categoriaRepository=categoriaRepository;
    }
    private Categoria getCategoriaById(Long id) {
        return categoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada."));
    }
    @Override
    public Producto save(ProductoDto dto){
        if (dto.getPrecio() <= 0) {
            throw new IllegalArgumentException("El precio del producto debe ser positivo.");
        }
        Producto producto=new Producto();
        producto.setNombre(dto.getNombre());
        producto.setPrecio(dto.getPrecio());
        producto.setCategoria(getCategoriaById(dto.getCategoriaId()));
        return productoRepository.save(producto);
    }
    @Override
    public List<Producto> findAll(){
        return productoRepository.findAll();
    }
    @Override
    public Producto update(Long id, ProductoDto dto) {
        return productoRepository.findById(id).map(producto -> {
            producto.setNombre(dto.getNombre());
            producto.setPrecio(dto.getPrecio());
            if (!producto.getCategoria().getId().equals(dto.getCategoriaId())) {
                producto.setCategoria(getCategoriaById(dto.getCategoriaId()));
            }
            return productoRepository.save(producto);
        }).orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + id));
    }
    @Override
    public Optional<Producto> findById(Long id) {
        return productoRepository.findById(id);
    }
    @Override
    public void deleteById(Long id) {
        productoRepository.deleteById(id);
    }
}
