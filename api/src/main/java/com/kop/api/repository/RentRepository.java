package com.kop.api.repository;

import com.kop.api.model.entity.Rent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RentRepository extends JpaRepository<Rent, Long> {
    
    List<Rent> findByStatus(Rent.RentStatus status);
    
    List<Rent> findByMonthContaining(String month);
    
    List<Rent> findByMonthStartingWith(String yearMonth);
}