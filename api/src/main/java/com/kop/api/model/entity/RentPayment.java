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
@Table(name = "rent_payments")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RentPayment {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal amount;
    
    @Column(name = "due_date", nullable = false)
    private LocalDate dueDate;
    
    @Column(name = "paid_date")
    private LocalDate paidDate;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private PaymentStatus status;
    
    @Column(nullable = false)
    private Integer month;
    
    @Column(nullable = false)
    private Integer year;
    
    @Column(length = 500)
    private String note;
    
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (status == null && paidDate != null) {
            status = PaymentStatus.PAID;
        } else if (status == null) {
            status = PaymentStatus.PENDING;
        }
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
        if (paidDate != null && status != PaymentStatus.PAID) {
            status = PaymentStatus.PAID;
        }
    }
    
    public enum PaymentStatus {
        PAID,
        PENDING,
        OVERDUE
    }
}