package com.kop.api.controller;

import com.kop.api.model.dto.CategoryDTO;
import com.kop.api.model.entity.Category;
import com.kop.api.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
@Tag(name = "Categories", description = "Category management APIs")
@SecurityRequirement(name = "X-API-Key")
public class CategoryController {
    
    private final CategoryService categoryService;
    
    @GetMapping
    @Operation(summary = "Get all categories", description = "Retrieve all categories with optional type filter")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved categories")
    public ResponseEntity<List<CategoryDTO>> getAllCategories(
            @RequestParam(required = false) Category.CategoryType type) {
        List<CategoryDTO> categories = categoryService.getAllCategories(type);
        return ResponseEntity.ok(categories);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable Long id) {
        CategoryDTO category = categoryService.getCategoryById(id);
        return ResponseEntity.ok(category);
    }
    
    @GetMapping("/by-id/{categoryId}")
    public ResponseEntity<CategoryDTO> getCategoryByCategoryId(@PathVariable String categoryId) {
        CategoryDTO category = categoryService.getCategoryByCategoryId(categoryId);
        return ResponseEntity.ok(category);
    }
    
    @PostMapping
    public ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody CategoryDTO dto) {
        CategoryDTO created = categoryService.createCategory(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<CategoryDTO> updateCategory(
            @PathVariable Long id,
            @Valid @RequestBody CategoryDTO dto) {
        CategoryDTO updated = categoryService.updateCategory(id, dto);
        return ResponseEntity.ok(updated);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
}
