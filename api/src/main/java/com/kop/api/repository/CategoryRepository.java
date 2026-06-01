package com.kop.api.repository;

import com.kop.api.model.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    
    Optional<Category> findByCode(String code);
    
    List<Category> findByType(Category.CategoryType type);
    
    boolean existsByCode(String code);
    
    @Query("SELECT c FROM Category c WHERE (:type IS NULL OR c.type = :type) ORDER BY c.name")
    List<Category> findWithFilters(@Param("type") Category.CategoryType type);
    
    @Query("SELECT DISTINCT c.type FROM Category c")
    List<Category.CategoryType> findAllTypes();
}