package com.kop.api.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "stock_operations")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StockOperation {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, length = 20)
    private String ticker;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private OperationType type;
    
    @Column(nullable = false)
    private Integer quantity;
    
    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal price;
    
    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal total;
    
    @Column(nullable = false)
    private LocalDate date;
    
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (total == null || total.compareTo(BigDecimal.ZERO) == 0) {
            total = price.multiply(BigDecimal.valueOf(quantity));
        }
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
        total = price.multiply(BigDecimal.valueOf(quantity));
    }
    
    public enum OperationType {
        BUY,
        SELL
    }
}