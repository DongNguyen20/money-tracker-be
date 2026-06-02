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
public class RentDetailDTO {
    
    private Long id;
    
    private Long rentId;
    
    @DecimalMin(value = "0.0", message = "Base amount must be positive")
    private Double baseAmount;
    
    @DecimalMin(value = "0.0", message = "Wifi amount must be positive")
    private Double wifiAmount;
    
    @DecimalMin(value = "0.0", message = "Water amount must be positive")
    private Double waterAmount;
    
    @DecimalMin(value = "0.0", message = "Electricity amount must be positive")
    private Double electricityAmount;
    
    @DecimalMin(value = "0.0", message = "Other amount must be positive")
    private Double otherAmount;
    
    @Min(value = 0, message = "KWH consumed must be positive")
    private Integer kwhConsumed;
    
    @Min(value = 0, message = "M3 consumed must be positive")
    private Integer m3Consumed;
    
    @Size(max = 500, message = "Notes must not exceed 500 characters")
    private String notes;
    
    // Response fields
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}