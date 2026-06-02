package com.kop.api.repository;

import com.kop.api.model.entity.ConfigParam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ConfigParamRepository extends JpaRepository<ConfigParam, Long> {
    
    List<ConfigParam> findByParamKeyContainingIgnoreCase(String paramKey);
    
    Optional<ConfigParam> findByParamKey(String paramKey);
    
    boolean existsByParamKey(String paramKey);
}
