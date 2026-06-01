package com.kop.api.repository;

import com.kop.api.model.entity.StockOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface StockOperationRepository extends JpaRepository<StockOperation, Long> {
    
    Page<StockOperation> findByTicker(String ticker, Pageable pageable);
    
    Page<StockOperation> findByType(StockOperation.OperationType type, Pageable pageable);
    
    Page<StockOperation> findByTickerAndType(String ticker, StockOperation.OperationType type, Pageable pageable);
    
    @Query("SELECT so FROM StockOperation so WHERE " +
           "(:ticker IS NULL OR so.ticker = :ticker) AND " +
           "(:type IS NULL OR so.type = :type) " +
           "ORDER BY so.date DESC, so.createdAt DESC")
    Page<StockOperation> findWithFilters(
            @Param("ticker") String ticker,
            @Param("type") StockOperation.OperationType type,
            Pageable pageable);
    
    @Query("SELECT so.ticker, SUM(CASE WHEN so.type = 'BUY' THEN so.quantity ELSE -so.quantity END) as netQuantity, " +
           "SUM(CASE WHEN so.type = 'BUY' THEN so.quantity * so.price ELSE 0 END) as totalBuy, " +
           "SUM(CASE WHEN so.type = 'SELL' THEN so.quantity * so.price ELSE 0 END) as totalSell " +
           "FROM StockOperation so " +
           "GROUP BY so.ticker")
    List<Object[]> calculatePortfolioSummary();
    
    @Query("SELECT so FROM StockOperation so WHERE so.ticker = :ticker ORDER BY so.date DESC")
    List<StockOperation> findByTickerOrderByDateDesc(@Param("ticker") String ticker);
}