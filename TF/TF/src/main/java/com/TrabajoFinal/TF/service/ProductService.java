package com.TrabajoFinal.TF.service;

import com.TrabajoFinal.TF.entity.Category;
import com.TrabajoFinal.TF.entity.Product;
import com.TrabajoFinal.TF.entity.DTO.ProductDTO;
import com.TrabajoFinal.TF.repository.CategoryRepository;
import com.TrabajoFinal.TF.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    public ProductDTO create(ProductDTO dto) {
        if (dto.getCategory() == null || dto.getCategory().getId() == null) {
            throw new IllegalArgumentException("Debe especificar una categoría válida");
        }

        Category category = categoryRepository.findById(dto.getCategory().getId())
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

        Product product = Product.builder()
                .name(dto.getName())
                .price(dto.getPrice())
                .category(category)
                .build();

        productRepository.save(product);
        return ProductDTO.fromEntity(product);
    }

    // ✅ Nuevo metodo update
    public ProductDTO update(Long id, ProductDTO dto) {
        Product existing = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        if (dto.getCategory() != null && dto.getCategory().getId() != null) {
            Category category = categoryRepository.findById(dto.getCategory().getId())
                    .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
            existing.setCategory(category);
        }

        existing.setName(dto.getName());
        existing.setPrice(dto.getPrice());

        productRepository.save(existing);
        return ProductDTO.fromEntity(existing);
    }

    public List<ProductDTO> findAll() {
        return productRepository.findAll()
                .stream()
                .map(ProductDTO::fromEntity)
                .collect(Collectors.toList());
    }

    public ProductDTO findById(Long id) {
        return productRepository.findById(id)
                .map(ProductDTO::fromEntity)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
    }

    public void delete(Long id) {
        productRepository.deleteById(id);
    }

}





