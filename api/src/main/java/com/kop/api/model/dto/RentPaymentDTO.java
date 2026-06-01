package com.kop.api.model.dto;

import com.kop.api.model.entity.RentPayment;
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
public class RentPaymentDTO {
    
    private Long id;
    
    @NotNull(message = "Amount is required")
    @Positive(message = "Amount must be positive")
    private BigDecimal amount;
    
    @NotNull(message = "Due date is required")
    private LocalDate dueDate;
    
    private LocalDate paidDate;
    
    private RentPayment.PaymentStatus status;
    
    @NotNull(message = "Month is required")
    @Min(value = 1, message = "Month must be between 1 and 12")
    @Max(value = 12, message = "Month must be between 1 and 12")
    private Integer month;
    
    @NotNull(message = "Year is required")
    @Min(value = 2000, message = "Year must be valid")
    private Integer year;
    
    @Size(max = 500, message = "Note must not exceed 500 characters")
    private String note;
    
    // Response fields
    private java.time.LocalDateTime createdAt;
    private java.time.LocalDateTime updatedAt;
}