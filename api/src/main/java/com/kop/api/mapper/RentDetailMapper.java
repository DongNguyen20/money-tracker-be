package com.kop.api.mapper;

import com.kop.api.model.dto.RentDetailDTO;
import com.kop.api.model.entity.RentDetail;
import org.springframework.stereotype.Component;

@Component
public class RentDetailMapper {
    
    public RentDetailDTO toDTO(RentDetail entity) {
        if (entity == null) {
            return null;
        }
        
        return RentDetailDTO.builder()
                .id(entity.getId())
                .rentId(entity.getRentId())
                .baseAmount(entity.getBaseAmount())
                .wifiAmount(entity.getWifiAmount())
                .waterAmount(entity.getWaterAmount())
                .electricityAmount(entity.getElectricityAmount())
                .otherAmount(entity.getOtherAmount())
                .kwhConsumed(entity.getKwhConsumed())
                .m3Consumed(entity.getM3Consumed())
                .notes(entity.getNotes())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
    
    public RentDetail toEntity(RentDetailDTO dto) {
        if (dto == null) {
            return null;
        }
        
        return RentDetail.builder()
                .rentId(dto.getRentId())
                .baseAmount(dto.getBaseAmount())
                .wifiAmount(dto.getWifiAmount())
                .waterAmount(dto.getWaterAmount())
                .electricityAmount(dto.getElectricityAmount())
                .otherAmount(dto.getOtherAmount())
                .kwhConsumed(dto.getKwhConsumed())
                .m3Consumed(dto.getM3Consumed())
                .notes(dto.getNotes())
                .build();
    }
    
    public void updateEntityFromDTO(RentDetailDTO dto, RentDetail entity) {
        if (dto == null || entity == null) {
            return;
        }
        
        if (dto.getRentId() != null) {
            entity.setRentId(dto.getRentId());
        }
        if (dto.getBaseAmount() != null) {
            entity.setBaseAmount(dto.getBaseAmount());
        }
        if (dto.getWifiAmount() != null) {
            entity.setWifiAmount(dto.getWifiAmount());
        }
        if (dto.getWaterAmount() != null) {
            entity.setWaterAmount(dto.getWaterAmount());
        }
        if (dto.getElectricityAmount() != null) {
            entity.setElectricityAmount(dto.getElectricityAmount());
        }
        if (dto.getOtherAmount() != null) {
            entity.setOtherAmount(dto.getOtherAmount());
        }
        if (dto.getKwhConsumed() != null) {
            entity.setKwhConsumed(dto.getKwhConsumed());
        }
        if (dto.getM3Consumed() != null) {
            entity.setM3Consumed(dto.getM3Consumed());
        }
        if (dto.getNotes() != null) {
            entity.setNotes(dto.getNotes());
        }
    }
}