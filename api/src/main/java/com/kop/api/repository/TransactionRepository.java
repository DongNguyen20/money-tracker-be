package com.kop.api.repository;

import com.kop.api.model.entity.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    
    Page<Transaction> findByType(Transaction.TransactionType type, Pageable pageable);
    
    Page<Transaction> findByCategoryId(String categoryId, Pageable pageable);
    
    Page<Transaction> findByTypeAndCategoryId(Transaction.TransactionType type, String categoryId, Pageable pageable);
    
    Page<Transaction> findByDateBetween(LocalDate startDate, LocalDate endDate, Pageable pageable);
    
    @Query("SELECT t FROM Transaction t WHERE " +
           "(:type IS NULL OR t.type = :type) AND " +
           "(:categoryId IS NULL OR t.categoryId = :categoryId) AND " +
           "(:dateFrom IS NULL OR t.date >= :dateFrom) AND " +
           "(:dateTo IS NULL OR t.date <= :dateTo)")
    Page<Transaction> findWithFilters(
            @Param("type") Transaction.TransactionType type,
            @Param("categoryId") String categoryId,
            @Param("dateFrom") LocalDate dateFrom,
            @Param("dateTo") LocalDate dateTo,
            Pageable pageable);
    
    @Query("SELECT COALESCE(SUM(t.amount), 0) FROM Transaction t WHERE t.type = :type AND " +
           "(:month IS NULL OR EXTRACT(MONTH FROM t.date) = :month) AND " +
           "(:year IS NULL OR EXTRACT(YEAR FROM t.date) = :year)")
    BigDecimal sumByTypeAndPeriod(
            @Param("type") Transaction.TransactionType type,
            @Param("month") Integer month,
            @Param("year") Integer year);
    
    @Query("SELECT t FROM Transaction t WHERE " +
           "EXTRACT(MONTH FROM t.date) = :month AND " +
           "EXTRACT(YEAR FROM t.date) = :year " +
           "ORDER BY t.date DESC, t.createdAt DESC")
    List<Transaction> findByMonthAndYear(
            @Param("month") int month,
            @Param("year") int year);
    
    @Query("SELECT t FROM Transaction t ORDER BY t.date DESC, t.createdAt DESC")
    List<Transaction> findRecentTransactions(Pageable pageable);
    
    Long countByCategoryId(String categoryId);
}