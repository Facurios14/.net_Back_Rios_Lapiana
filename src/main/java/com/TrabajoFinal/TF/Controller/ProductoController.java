package com.TrabajoFinal.TF.Controller;

import com.TrabajoFinal.TF.entity.DTO.ProductoDto;
import com.TrabajoFinal.TF.entity.Producto;
import com.TrabajoFinal.TF.entity.mapper.ProductoMapper;
import com.TrabajoFinal.TF.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
    @RestController
    @RequestMapping("/api/productos")
    public class ProductoController {

        private final ProductoService productoService;

        @Autowired
        public ProductoController(ProductoService productoService) {
            this.productoService = productoService;
        }

        @PostMapping
        public ResponseEntity<ProductoDto> createProducto(@RequestBody ProductoDto dto) {
            try {
                Producto nuevoProducto = productoService.save(dto);
                return ResponseEntity.status(HttpStatus.CREATED).body(ProductoMapper.toDto(nuevoProducto)); // 201 Created
            } catch (IllegalArgumentException e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build(); // 400 Bad Request
            }
        }

        @GetMapping
        public ResponseEntity<List<ProductoDto>> getAllProductos() {
            List<Producto> productos=productoService.findAll();
            return ResponseEntity.ok(ProductoMapper.toDtoList(productos));
        }

        @GetMapping("/{id}")
        public ResponseEntity<ProductoDto> getProductoById(@PathVariable Long id) {
            return productoService.findById(id)
                    .map(ProductoMapper::toDto)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        }

        @PutMapping("/{id}")
        public ResponseEntity<ProductoDto> updateProducto(@PathVariable Long id, @RequestBody ProductoDto dto) {
            try {
                Producto productoActualizado = productoService.update(id, dto);
                return ResponseEntity.ok(ProductoMapper.toDto(productoActualizado));
            } catch (RuntimeException e) {
                return ResponseEntity.notFound().build();
            }
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<Void> deleteProducto(@PathVariable Long id) {
            productoService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
    }

