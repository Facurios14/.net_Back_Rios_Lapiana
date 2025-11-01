package com.TrabajoFinal.TF.service;

import com.TrabajoFinal.TF.entity.Category;
import com.TrabajoFinal.TF.entity.DTO.CategoryDTO;
import com.TrabajoFinal.TF.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    private final CategoryRepository repository;

    public CategoryService(CategoryRepository repository) {
        this.repository = repository;
    }

    public CategoryDTO create(String name) {
        Category category = Category.builder()
                .name(name)
                .build();

        Category saved = repository.save(category);
        return CategoryDTO.fromEntity(saved); // ✅ cambio aquí
    }

    public List<CategoryDTO> findAll() {
        return repository.findAll().stream()
                .map(CategoryDTO::fromEntity) // ✅ y aquí también
                .collect(Collectors.toList());
    }

    public Category save(Category category) {
        return repository.save(category);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public CategoryDTO update(Long id, CategoryDTO dto) {
        // Busca la categoría por ID y usa .map() para actualizarla si existe
        return repository.findById(id)
                .map(category -> {
                    // Actualiza solo el campo 'name' con el valor del DTO
                    category.setName(dto.getName());

                    // Guarda la entidad actualizada y mapea el resultado a DTO
                    Category saved = repository.save(category);
                    return CategoryDTO.fromEntity(saved);
                })
                .orElseThrow(() -> new RuntimeException("Categoría con ID " + id + " no encontrada")); // Manejo de error si no existe
    }

/*    public Category update(Long id, Category updatedCategory) {
        return repository.findById(id)
                .map(category -> {
                    category.setName(updatedCategory.getName());
                    return repository.save(category);
                })
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
    }*/
}

    /*private final CategoryRepository repository;

    public CategoryService(CategoryRepository repository) {
        this.repository = repository;
    }

    public CategoryDTO create(String name) {
        Category category = Category.builder().name(name).build();
        return new CategoryDTO(repository.save(category));
    }

    public List<CategoryDTO> findAll() {
        return repository.findAll().stream()
                .map(CategoryDTO::new)
                .collect(Collectors.toList());
    }

    public Category save(Category category) {
        return repository.save(category);
    }

    public Category update(Long id, Category updatedCategory) {
        return repository.findById(id)
                .map(category -> {
                    category.setName(updatedCategory.getName());
                    return repository.save(category);
                })
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }*/

