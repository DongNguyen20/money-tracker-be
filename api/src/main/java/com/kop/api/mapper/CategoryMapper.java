package com.kop.api.mapper;

import com.kop.api.model.dto.CategoryDTO;
import com.kop.api.model.entity.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
    componentModel = "spring",
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface CategoryMapper {
    
    @Mapping(target = "transactionCount", ignore = true)
    CategoryDTO toDTO(Category category);
    
    Category toEntity(CategoryDTO dto);
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntityFromDTO(CategoryDTO dto, @MappingTarget Category category);
}
