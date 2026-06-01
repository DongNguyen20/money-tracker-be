package com.kop.api.model.dto;

import com.kop.api.model.entity.Transaction;
import jakarta.validation.constraints.*;
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
public class TransactionDTO {
    
    private Long id;
    
    @NotNull(message = "Amount is required")
    @Positive(message = "Amount must be positive")
    private BigDecimal amount;
    
    @NotNull(message = "Type is required")
    private Transaction.TransactionType type;
    
    @NotBlank(message = "Category ID is required")
    private String categoryId;
    
    @Size(max = 500, message = "Note must not exceed 500 characters")
    private String note;
    
    @NotNull(message = "Date is required")
    private LocalDate date;
    
    // Response fields
    private String categoryName;
    private String categoryIcon;
    private String categoryColor;
    private java.time.LocalDateTime createdAt;
    private java.time.LocalDateTime updatedAt;
}