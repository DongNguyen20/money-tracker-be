package com.kop.api.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "rent_detail")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RentDetail {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "rent_id", nullable = false)
    private Long rentId;
    
    @Column(nullable = false)
    private Double baseAmount;
    
    @Column(nullable = false)
    private Double wifiAmount;
    
    @Column(nullable = false)
    private Double waterAmount;
    
    @Column(nullable = false)
    private Double electricityAmount;
    
    @Column(nullable = false)
    private Double otherAmount;
    
    @Column(name = "kwh_consumed")
    private Integer kwhConsumed;
    
    @Column(name = "m3_consumed")
    private Integer m3Consumed;
    
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
        if (baseAmount == null) baseAmount = 0.0;
        if (wifiAmount == null) wifiAmount = 0.0;
        if (waterAmount == null) waterAmount = 0.0;
        if (electricityAmount == null) electricityAmount = 0.0;
        if (otherAmount == null) otherAmount = 0.0;
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}