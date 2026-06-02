package com.kop.api.repository;

import com.kop.api.model.entity.RentDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RentDetailRepository extends JpaRepository<RentDetail, Long> {
    
    List<RentDetail> findByRentId(Long rentId);
    
    void deleteByRentId(Long rentId);
}