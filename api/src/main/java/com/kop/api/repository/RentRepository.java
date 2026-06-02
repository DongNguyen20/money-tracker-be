package com.kop.api.repository;

import com.kop.api.model.entity.Rent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface RentRepository extends JpaRepository<Rent, Long> {
    
    List<Rent> findByDateBetween(LocalDate startDate, LocalDate endDate);
    
    List<Rent> findByStatus(Rent.RentStatus status);
    
    List<Rent> findByDateBetweenAndStatus(LocalDate startDate, LocalDate endDate, Rent.RentStatus status);
    
    List<Rent> findByDate(LocalDate date);
}