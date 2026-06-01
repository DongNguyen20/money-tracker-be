package com.kop.api.repository;

import com.kop.api.model.entity.StockPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StockPriceRepository extends JpaRepository<StockPrice, Long> {
    
    Optional<StockPrice> findByTicker(String ticker);
    
    boolean existsByTicker(String ticker);
}