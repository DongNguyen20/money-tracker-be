package com.kop.api.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "categories")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Category {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true, length = 50)
    private String categoryId;
    
    @Column(nullable = false, length = 100)
    private String name;
    
    @Column(length = 10)
    private String icon;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private CategoryType type;
    
    @Column(length = 20)
    private String color;
    
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (categoryId == null || categoryId.isEmpty()) {
            categoryId = "cat_" + System.currentTimeMillis();
        }
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
    
    public enum CategoryType {
        INCOME,
        EXPENSE,
        SAVING
    }
}