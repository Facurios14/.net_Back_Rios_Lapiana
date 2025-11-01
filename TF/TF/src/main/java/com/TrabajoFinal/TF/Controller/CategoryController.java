package com.TrabajoFinal.TF.Controller;

import com.TrabajoFinal.TF.entity.Category;
import com.TrabajoFinal.TF.entity.DTO.CategoryDTO;
import com.TrabajoFinal.TF.entity.DTO.ProductDTO;
import com.TrabajoFinal.TF.service.CategoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@CrossOrigin(origins = "http://localhost:5173")
public class CategoryController {

    private final CategoryService service;

    public CategoryController(CategoryService service) {
        this.service = service;
    }

    @PostMapping
    public CategoryDTO create(@RequestBody CategoryDTO dto) {
        return service.create(dto.getName());
    }

    @GetMapping
    public List<CategoryDTO> getAll() {
        return service.findAll();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    @PutMapping("/{id}") // ✅ Metodo PUT habilitado
    public CategoryDTO update(@PathVariable Long id, @RequestBody CategoryDTO dto) {
        return service.update(id, dto);
    }


}


/*    @PutMapping("/{id}")
    public Category update(@PathVariable Long id, @RequestBody Category category) {
        return categoryService.update(id, category);
    }*/


