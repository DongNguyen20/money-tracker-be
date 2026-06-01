package com.kop.api.repository;

import com.kop.api.model.entity.RentPayment;
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
public interface RentPaymentRepository extends JpaRepository<RentPayment, Long> {
    
    Page<RentPayment> findByStatus(RentPayment.PaymentStatus status, Pageable pageable);
    
    Page<RentPayment> findByMonthAndYear(Integer month, Integer year, Pageable pageable);
    
    Page<RentPayment> findByMonthAndYearAndStatus(Integer month, Integer year, RentPayment.PaymentStatus status, Pageable pageable);
    
    @Query("SELECT rp FROM RentPayment rp WHERE " +
           "(:status IS NULL OR rp.status = :status) AND " +
           "(:month IS NULL OR rp.month = :month) AND " +
           "(:year IS NULL OR rp.year = :year) " +
           "ORDER BY rp.dueDate DESC")
    Page<RentPayment> findWithFilters(
            @Param("status") RentPayment.PaymentStatus status,
            @Param("month") Integer month,
            @Param("year") Integer year,
            Pageable pageable);
    
    @Query("SELECT COALESCE(SUM(rp.amount), 0) FROM RentPayment rp WHERE rp.status = 'PAID' AND " +
           "(:year IS NULL OR rp.year = :year)")
    BigDecimal sumPaidByYear(@Param("year") Integer year);
    
    @Query("SELECT COALESCE(SUM(rp.amount), 0) FROM RentPayment rp WHERE rp.status = 'PENDING' AND " +
           "(:year IS NULL OR rp.year = :year)")
    BigDecimal sumPendingByYear(@Param("year") Integer year);
    
    @Query("SELECT rp FROM RentPayment rp WHERE rp.dueDate < :currentDate AND rp.status != 'PAID'")
    List<RentPayment> findOverduePayments(@Param("currentDate") LocalDate currentDate);
}