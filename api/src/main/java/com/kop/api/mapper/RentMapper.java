package com.kop.api.mapper;

import com.kop.api.model.dto.RentDTO;
import com.kop.api.model.entity.Rent;
import org.springframework.stereotype.Component;

@Component
public class RentMapper {
    
    public RentDTO toDTO(Rent entity) {
        if (entity == null) {
            return null;
        }
        
        return RentDTO.builder()
                .id(entity.getId())
                .month(entity.getMonth())
                .base(entity.getBase())
                .wifi(entity.getWifi())
                .garbage(entity.getGarbage())
                .other(entity.getOther())
                .elecUsage(entity.getElecUsage())
                .waterUsage(entity.getWaterUsage())
                .elecTotal(entity.getElecTotal())
                .waterTotal(entity.getWaterTotal())
                .total(entity.getTotal())
                .status(entity.getStatus())
                .notes(entity.getNotes())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
    
    public Rent toEntity(RentDTO dto) {
        if (dto == null) {
            return null;
        }
        
        return Rent.builder()
                .month(dto.getMonth())
                .base(dto.getBase())
                .wifi(dto.getWifi())
                .garbage(dto.getGarbage())
                .other(dto.getOther())
                .elecUsage(dto.getElecUsage())
                .waterUsage(dto.getWaterUsage())
                .elecTotal(dto.getElecTotal())
                .waterTotal(dto.getWaterTotal())
                .total(dto.getTotal())
                .status(dto.getStatus())
                .notes(dto.getNotes())
                .build();
    }
    
    public void updateEntityFromDTO(RentDTO dto, Rent entity) {
        if (dto == null || entity == null) {
            return;
        }
        
        if (dto.getMonth() != null) {
            entity.setMonth(dto.getMonth());
        }
        if (dto.getBase() != null) {
            entity.setBase(dto.getBase());
        }
        if (dto.getWifi() != null) {
            entity.setWifi(dto.getWifi());
        }
        if (dto.getGarbage() != null) {
            entity.setGarbage(dto.getGarbage());
        }
        if (dto.getOther() != null) {
            entity.setOther(dto.getOther());
        }
        if (dto.getElecUsage() != null) {
            entity.setElecUsage(dto.getElecUsage());
        }
        if (dto.getWaterUsage() != null) {
            entity.setWaterUsage(dto.getWaterUsage());
        }
        if (dto.getElecTotal() != null) {
            entity.setElecTotal(dto.getElecTotal());
        }
        if (dto.getWaterTotal() != null) {
            entity.setWaterTotal(dto.getWaterTotal());
        }
        if (dto.getTotal() != null) {
            entity.setTotal(dto.getTotal());
        }
        if (dto.getStatus() != null) {
            entity.setStatus(dto.getStatus());
        }
        if (dto.getNotes() != null) {
            entity.setNotes(dto.getNotes());
        }
    }
}