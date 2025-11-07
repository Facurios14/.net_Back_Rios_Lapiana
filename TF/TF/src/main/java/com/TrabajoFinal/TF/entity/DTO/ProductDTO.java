package com.TrabajoFinal.TF.entity.DTO;

import com.TrabajoFinal.TF.entity.Product;
import com.TrabajoFinal.TF.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDTO {

    private Long id;
    private String name;
    private Double price;
    private CategoryDTO category;
    private String imageUrl;
    private Integer stock;
    private String status;

    // Conversión de entidad a DTO
    public static ProductDTO fromEntity(Product product) {
        if (product == null) {
            return null;
        }

        return ProductDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .imageUrl(product.getImageUrl()) // Mapear el nuevo campo
                .category(CategoryDTO.fromEntity(product.getCategory()))
                .stock(product.getStock())
                .status(product.getStatus())
                .build();
    }

    // Conversión de DTO a entidad
    public Product toEntity(Category category) {
        return Product.builder()
                .id(this.id)
                .name(this.name)
                .price(this.price)
                .imageUrl(this.imageUrl)
                .category(category)
                .stock(this.stock)
                .status(this.status)
                .build();
    }
}

