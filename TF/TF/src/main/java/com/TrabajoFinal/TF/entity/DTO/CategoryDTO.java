package com.TrabajoFinal.TF.entity.DTO;

import com.TrabajoFinal.TF.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryDTO {

    private Long id;
    private String name;
    private String imageUrl;

    public static CategoryDTO fromEntity(Category category) {
        if (category == null) {
            return null;
        }

        return CategoryDTO.builder()
                .id(category.getId())
                .name(category.getName())
                .imageUrl(category.getImageUrl()) // Mapear el nuevo campo
                .build();
    }
}


