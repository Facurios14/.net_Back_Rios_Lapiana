package com.TrabajoFinal.TF.Controller;

import com.TrabajoFinal.TF.entity.Categoria;
import com.TrabajoFinal.TF.entity.DTO.CategoriaDto;
import com.TrabajoFinal.TF.entity.mapper.CategoriaMapper;
import com.TrabajoFinal.TF.service.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/catergorias")
public class CategoriaController {
    private CategoriaService categoriaService;
    @Autowired
    public CategoriaController(CategoriaService categoriaService){
        this.categoriaService=categoriaService;
    }
    @PostMapping
    public ResponseEntity<CategoriaDto> createCategoria(@RequestBody CategoriaDto dto) {
        Categoria nuevaCategoria = categoriaService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(CategoriaMapper.toDto(nuevaCategoria)); // 201 Created
    }
    @GetMapping
    public ResponseEntity<List<CategoriaDto>> getAllCategorias() {
        List<Categoria> categorias=categoriaService.findAll();
        return ResponseEntity.ok(CategoriaMapper.toDtoList(categorias)); // 200 OK
    }
    @GetMapping("/{id}")
    public ResponseEntity<CategoriaDto> getCategoriaById(@PathVariable Long id) {
        return categoriaService.findById(id)
                .map(CategoriaMapper::toDto)
                .map(ResponseEntity::ok)// 200 OK
                .orElse(ResponseEntity.notFound().build()); // 404 Not Found
    }
    @PutMapping("/{id}")
    public ResponseEntity<CategoriaDto> updateCategoria(@PathVariable Long id, @RequestBody CategoriaDto dto) {
        try {
            Categoria categoriaActualizada = categoriaService.update(id, dto);
            return ResponseEntity.ok(CategoriaMapper.toDto(categoriaActualizada)); // 200 OK
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build(); // 404 Not Found (si el ID no existe)
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategoria(@PathVariable Long id) {
        categoriaService.deleteById(id);
        return ResponseEntity.noContent().build(); // 204 No Content
    }
}
