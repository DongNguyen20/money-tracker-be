package com.kop.api.service;

import com.kop.api.exception.ResourceNotFoundException;
import com.kop.api.mapper.RentMapper;
import com.kop.api.model.dto.RentDTO;
import com.kop.api.model.entity.Rent;
import com.kop.api.repository.RentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class RentService {
    
    private final RentRepository rentRepository;
    private final RentMapper rentMapper;
    
    public List<RentDTO> getAllRents() {
        List<Rent> rents = rentRepository.findAll();
        return rents.stream()
                .map(rentMapper::toDTO)
                .collect(Collectors.toList());
    }
    
    public List<RentDTO> getRentsByStatus(Rent.RentStatus status) {
        List<Rent> rents = rentRepository.findByStatus(status);
        return rents.stream()
                .map(rentMapper::toDTO)
                .collect(Collectors.toList());
    }
    
    public RentDTO getRentById(Long id) {
        Rent rent = rentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Rent not found with id: " + id));
        return rentMapper.toDTO(rent);
    }
    
    @Transactional
    public RentDTO createRent(RentDTO dto) {
        Rent rent = rentMapper.toEntity(dto);
        Rent savedRent = rentRepository.save(rent);
        log.info("Created rent with id: {}", savedRent.getId());
        return rentMapper.toDTO(savedRent);
    }
    
    @Transactional
    public RentDTO updateRent(Long id, RentDTO dto) {
        Rent existing = rentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Rent not found with id: " + id));
        
        rentMapper.updateEntityFromDTO(dto, existing);
        Rent updated = rentRepository.save(existing);
        log.info("Updated rent with id: {}", updated.getId());
        return rentMapper.toDTO(updated);
    }
    
    @Transactional
    public void deleteRent(Long id) {
        Rent rent = rentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Rent not found with id: " + id));
        rentRepository.delete(rent);
        log.info("Deleted rent with id: {}", id);
    }
    
    @Transactional
    public RentDTO updateRentStatus(Long id, Rent.RentStatus status) {
        Rent rent = rentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Rent not found with id: " + id));
        
        rent.setStatus(status);
        Rent updated = rentRepository.save(rent);
        log.info("Updated rent status to {} for rent id: {}", status, id);
        return rentMapper.toDTO(updated);
    }
}