package com.kop.api.repository;

import com.kop.api.model.entity.StockCash;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface StockCashRepository extends JpaRepository<StockCash, Long> {
    
    List<StockCash> findAllByOrderByDateDesc();
    
    @Query("SELECT COALESCE(SUM(CASE WHEN sc.type = 'DEPOSIT' THEN sc.amount ELSE -sc.amount END), 0) FROM StockCash sc")
    BigDecimal calculateBalance();
    
    @Query("SELECT sc FROM StockCash sc ORDER BY sc.date DESC")
    List<StockCash> findRecentTransactions(org.springframework.data.domain.Pageable pageable);
}