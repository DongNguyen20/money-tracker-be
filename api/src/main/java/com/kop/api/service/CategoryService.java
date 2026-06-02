package com.kop.api.service;

import com.kop.api.mapper.CategoryMapper;
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
    private final CategoryMapper categoryMapper;
    
    public List<CategoryDTO> getAllCategories(Category.CategoryType type) {
        List<Category> categories = type != null 
                ? categoryRepository.findByType(type)
                : categoryRepository.findAll();
        
        return categories.stream()
                .map(category -> {
                    CategoryDTO dto = categoryMapper.toDTO(category);
                    dto.setTransactionCount(transactionRepository.countByCategoryId(category.getId()));
                    return dto;
                })
                .collect(Collectors.toList());
    }
    
    public CategoryDTO getCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));
        CategoryDTO dto = categoryMapper.toDTO(category);
        dto.setTransactionCount(transactionRepository.countByCategoryId(category.getId()));
        return dto;
    }
    
    public CategoryDTO getCategoryByCode(String code) {
        Category category = categoryRepository.findByCode(code)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with code: " + code));
        CategoryDTO dto = categoryMapper.toDTO(category);
        dto.setTransactionCount(transactionRepository.countByCategoryId(category.getId()));
        return dto;
    }
    
    @Transactional
    public CategoryDTO createCategory(CategoryDTO dto) {
        // Check if code already exists
        if (dto.getCode() != null && categoryRepository.existsByCode(dto.getCode())) {
            throw new BusinessException("Category with code " + dto.getCode() + " already exists");
        }
        
        Category category = categoryMapper.toEntity(dto);
        Category saved = categoryRepository.save(category);
        log.info("Created category with id: {}", saved.getId());
        return categoryMapper.toDTO(saved);
    }
    
    @Transactional
    public CategoryDTO updateCategory(Long id, CategoryDTO dto) {
        Category existing = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));
        
        // Check if new code conflicts with existing
        if (dto.getCode() != null && !dto.getCode().equals(existing.getCode())) {
            if (categoryRepository.existsByCode(dto.getCode())) {
                throw new BusinessException("Category with code " + dto.getCode() + " already exists");
            }
        }
        
        categoryMapper.updateEntityFromDTO(dto, existing);
        Category updated = categoryRepository.save(existing);
        log.info("Updated category with id: {}", updated.getId());
        return categoryMapper.toDTO(updated);
    }
    
    @Transactional
    public void deleteCategory(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));
        
        // Check if category has transactions
        long transactionCount = transactionRepository.countByCategoryId(category.getId());
        if (transactionCount > 0) {
            throw new BusinessException("Cannot delete category with existing transactions. Transaction count: " + transactionCount);
        }
        
        categoryRepository.delete(category);
        log.info("Deleted category with id: {}", id);
    }
}