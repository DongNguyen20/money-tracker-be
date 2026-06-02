package com.kop.api.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "rent")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Rent {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String month; // Format: YYYY-MM
    
    @Column(nullable = false)
    private Double base; // Rent base amount
    
    @Column(nullable = false)
    private Double wifi; // WiFi amount
    
    @Column(nullable = false)
    private Double garbage; // Garbage amount
    
    @Column
    private Double other; // Other amount
    
    @Column(nullable = false)
    private Integer elecUsage; // Electricity usage in kWh
    
    @Column(nullable = false)
    private Integer waterUsage; // Water usage in m3
    
    @Column(nullable = false)
    private Double elecTotal; // Electricity total cost
    
    @Column(nullable = false)
    private Double waterTotal; // Water total cost
    
    @Column(nullable = false)
    private Double total; // Grand total
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RentStatus status;
    
    @Column(length = 500)
    private String notes;
    
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = null;
        if (status == null) {
            status = RentStatus.PAID;
        }
        if (base == null) base = 0.0;
        if (wifi == null) wifi = 0.0;
        if (garbage == null) garbage = 0.0;
        if (other == null) other = 0.0;
        if (elecUsage == null) elecUsage = 0;
        if (waterUsage == null) waterUsage = 0;
        if (elecTotal == null) elecTotal = 0.0;
        if (waterTotal == null) waterTotal = 0.0;
        if (total == null) total = 0.0;
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
    
    public enum RentStatus {
        PENDING,
        PAID,
        OVERDUE,
        PARTIALLY_PAID
    }
}