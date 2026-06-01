package com.kop.api.model.dto;

import com.kop.api.model.entity.StockOperation;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StockPortfolioDTO {
    
    private Long id;
    
    @NotBlank(message = "Ticker is required")
    @Size(max = 20, message = "Ticker must not exceed 20 characters")
    private String ticker;
    
    @NotNull(message = "Type is required")
    private StockOperation.OperationType type;
    
    @NotNull(message = "Quantity is required")
    @Positive(message = "Quantity must be positive")
    private Integer quantity;
    
    @NotNull(message = "Price is required")
    @Positive(message = "Price must be positive")
    private BigDecimal price;
    
    private BigDecimal total;
    
    @NotNull(message = "Date is required")
    private LocalDate date;
    
    // Response fields
    private java.time.LocalDateTime createdAt;
    private java.time.LocalDateTime updatedAt;
}