package com.kop.api.model.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConfigParamDTO {
    
    private Long id;
    
    @NotBlank(message = "Parameter key is required")
    @Size(max = 100, message = "Parameter key must not exceed 100 characters")
    private String paramKey;
    
    @Size(max = 500, message = "Parameter value must not exceed 500 characters")
    private String paramValue;
    
    @Size(max = 20, message = "Unit must not exceed 20 characters")
    private String unit;
    
    @Size(max = 500, message = "Description must not exceed 500 characters")
    private String description;
    
    // Response fields
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
