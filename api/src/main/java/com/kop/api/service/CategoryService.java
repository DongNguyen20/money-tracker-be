package com.kop.api.service;

import com.kop.api.model.dto.CategoryDTO;
import com.kop.api.model.entity.Category;
import com.kop.api.exception.ResourceNotFoundException;
import com.kop.api.exception.BusinessException;
import com.kop.api.repository.CategoryRepository;
import com.kop.api.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryService {
    
    private final CategoryRepository categoryRepository;
    private final TransactionRepository transactionRepository;
    
    public List<CategoryDTO> getAllCategories(Category.CategoryType type) {
        List<Category> categories = type != null 
                ? categoryRepository.findByType(type)
                : categoryRepository.findAll();
        
        return categories.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    public CategoryDTO getCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));
        return convertToDTO(category);
    }
    
    public CategoryDTO getCategoryByCategoryId(String categoryId) {
        Category category = categoryRepository.findByCategoryId(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with categoryId: " + categoryId));
        return convertToDTO(category);
    }
    
    @Transactional
    public CategoryDTO createCategory(CategoryDTO dto) {
        // Check if categoryId already exists
        if (dto.getCategoryId() != null && categoryRepository.existsByCategoryId(dto.getCategoryId())) {
            throw new BusinessException("Category with categoryId " + dto.getCategoryId() + " already exists");
        }
        
        Category category = convertToEntity(dto);
        Category saved = categoryRepository.save(category);
        log.info("Created category with id: {}", saved.getId());
        return convertToDTO(saved);
    }
    
    @Transactional
    public CategoryDTO updateCategory(Long id, CategoryDTO dto) {
        Category existing = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));
        
        // Check if new categoryId conflicts with existing
        if (dto.getCategoryId() != null && !dto.getCategoryId().equals(existing.getCategoryId())) {
            if (categoryRepository.existsByCategoryId(dto.getCategoryId())) {
                throw new BusinessException("Category with categoryId " + dto.getCategoryId() + " already exists");
            }
            existing.setCategoryId(dto.getCategoryId());
        }
        
        existing.setName(dto.getName());
        existing.setIcon(dto.getIcon());
        existing.setType(dto.getType());
        existing.setColor(dto.getColor());
        
        Category updated = categoryRepository.save(existing);
        log.info("Updated category with id: {}", updated.getId());
        return convertToDTO(updated);
    }
    
    @Transactional
    public void deleteCategory(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));
        
        // Check if category has transactions
        long transactionCount = transactionRepository.countByCategoryId(category.getCategoryId());
        if (transactionCount > 0) {
            throw new BusinessException("Cannot delete category with existing transactions. Transaction count: " + transactionCount);
        }
        
        categoryRepository.delete(category);
        log.info("Deleted category with id: {}", id);
    }
    
    private CategoryDTO convertToDTO(Category category) {
        Long transactionCount = transactionRepository.countByCategoryId(category.getCategoryId());
        
        return CategoryDTO.builder()
                .id(category.getId())
                .categoryId(category.getCategoryId())
                .name(category.getName())
                .icon(category.getIcon())
                .type(category.getType())
                .color(category.getColor())
                .transactionCount(transactionCount)
                .createdAt(category.getCreatedAt())
                .updatedAt(category.getUpdatedAt())
                .build();
    }
    
    private Category convertToEntity(CategoryDTO dto) {
        return Category.builder()
                .categoryId(dto.getCategoryId())
                .name(dto.getName())
                .icon(dto.getIcon())
                .type(dto.getType())
                .color(dto.getColor())
                .build();
    }
}