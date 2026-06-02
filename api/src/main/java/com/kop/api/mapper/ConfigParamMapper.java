package com.kop.api.mapper;

import com.kop.api.model.dto.ConfigParamDTO;
import com.kop.api.model.entity.ConfigParam;
import org.springframework.stereotype.Component;

@Component
public class ConfigParamMapper {
    
    public ConfigParamDTO toDTO(ConfigParam entity) {
        if (entity == null) {
            return null;
        }
        
        return ConfigParamDTO.builder()
                .id(entity.getId())
                .paramKey(entity.getParamKey())
                .paramValue(entity.getParamValue())
                .unit(entity.getUnit())
                .description(entity.getDescription())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
    
    public ConfigParam toEntity(ConfigParamDTO dto) {
        if (dto == null) {
            return null;
        }
        
        return ConfigParam.builder()
                .paramKey(dto.getParamKey())
                .paramValue(dto.getParamValue())
                .unit(dto.getUnit())
                .description(dto.getDescription())
                .build();
    }
    
    public void updateEntityFromDTO(ConfigParamDTO dto, ConfigParam entity) {
        if (dto == null || entity == null) {
            return;
        }
        
        if (dto.getParamKey() != null) {
            entity.setParamKey(dto.getParamKey());
        }
        if (dto.getParamValue() != null) {
            entity.setParamValue(dto.getParamValue());
        }
        if (dto.getUnit() != null) {
            entity.setUnit(dto.getUnit());
        }
        if (dto.getDescription() != null) {
            entity.setDescription(dto.getDescription());
        }
    }
}
