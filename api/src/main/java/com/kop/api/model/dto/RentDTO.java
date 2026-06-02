package com.kop.api.model.dto;

import com.kop.api.model.entity.Rent;
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
public class RentDTO {
    
    private Long id;
    
    @NotBlank(message = "Month is required")
    private String month; // Format: YYYY-MM
    
    @NotNull(message = "Base amount is required")
    @DecimalMin(value = "0.0", message = "Base amount must be positive")
    private Double base;
    
    @NotNull(message = "WiFi amount is required")
    @DecimalMin(value = "0.0", message = "WiFi amount must be positive")
    private Double wifi;
    
    @NotNull(message = "Garbage amount is required")
    @DecimalMin(value = "0.0", message = "Garbage amount must be positive")
    private Double garbage;
    
    @DecimalMin(value = "0.0", message = "Other amount must be positive")
    private Double other;
    
    @NotNull(message = "Electricity usage is required")
    @Min(value = 0, message = "Electricity usage must be positive")
    private Integer elecUsage;
    
    @NotNull(message = "Water usage is required")
    @Min(value = 0, message = "Water usage must be positive")
    private Integer waterUsage;
    
    @NotNull(message = "Electricity total is required")
    @DecimalMin(value = "0.0", message = "Electricity total must be positive")
    private Double elecTotal;
    
    @NotNull(message = "Water total is required")
    @DecimalMin(value = "0.0", message = "Water total must be positive")
    private Double waterTotal;
    
    @NotNull(message = "Total amount is required")
    @DecimalMin(value = "0.0", message = "Total amount must be positive")
    private Double total;
    
    private Rent.RentStatus status;
    
    @Size(max = 500, message = "Notes must not exceed 500 characters")
    private String notes;
    
    // Response fields
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}