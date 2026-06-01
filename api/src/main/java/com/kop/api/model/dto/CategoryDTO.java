package com.kop.api.model.dto;

import com.kop.api.model.entity.Category;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDTO {
    
    private Long id;
    
    @NotBlank(message = "Code is required")
    @Size(max = 20, message = "Code must not exceed 20 characters")
    private String code;
    
    @NotBlank(message = "Name is required")
    @Size(max = 100, message = "Name must not exceed 100 characters")
    private String name;
    
    @Size(max = 10, message = "Icon must not exceed 10 characters")
    private String icon;
    
    @NotNull(message = "Type is required")
    private Category.CategoryType type;
    
    @Size(max = 20, message = "Color must not exceed 20 characters")
    private String color;
    
    // Response fields
    private Long transactionCount;
    private java.time.LocalDateTime createdAt;
    private java.time.LocalDateTime updatedAt;
}