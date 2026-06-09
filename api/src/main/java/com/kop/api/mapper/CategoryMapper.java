package com.kop.api.mapper;

import com.kop.api.model.dto.CategoryDTO;
import com.kop.api.model.entity.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {
    
    public CategoryDTO toDTO(Category category) {
        if (category == null) {
            return null;
        }
        
        return CategoryDTO.builder()
                .id(category.getId())
                .name(category.getName())
                .code(category.getCode())
                .icon(category.getIcon())
                .color(category.getColor())
                .type(category.getType())
                .transactionCount(0L) // Will be set by service
                .build();
    }
    
    public Category toEntity(CategoryDTO dto) {
        if (dto == null) {
            return null;
        }
        
        return Category.builder()
                .name(dto.getName())
                .code(dto.getCode())
                .icon(dto.getIcon())
                .color(dto.getColor())
                .type(dto.getType())
                .build();
    }
    
    public void updateEntityFromDTO(CategoryDTO dto, Category category) {
        if (dto == null || category == null) {
            return;
        }
        
        if (dto.getName() != null) {
            category.setName(dto.getName());
        }
        if (dto.getCode() != null) {
            category.setCode(dto.getCode());
        }
        if (dto.getIcon() != null) {
            category.setIcon(dto.getIcon());
        }
        if (dto.getColor() != null) {
            category.setColor(dto.getColor());
        }
        if (dto.getType() != null) {
            category.setType(dto.getType());
        }
    }
}
