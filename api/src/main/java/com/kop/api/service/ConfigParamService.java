package com.kop.api.service;

import com.kop.api.exception.BusinessException;
import com.kop.api.exception.ResourceNotFoundException;
import com.kop.api.mapper.ConfigParamMapper;
import com.kop.api.model.dto.ConfigParamDTO;
import com.kop.api.model.entity.ConfigParam;
import com.kop.api.repository.ConfigParamRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ConfigParamService {
    
    private final ConfigParamRepository configParamRepository;
    private final ConfigParamMapper configParamMapper;
    
    public List<ConfigParamDTO> getAllConfigParams() {
        List<ConfigParam> configs = configParamRepository.findAll();
        return configs.stream()
                .map(configParamMapper::toDTO)
                .collect(Collectors.toList());
    }
    
    public ConfigParamDTO getConfigParamById(Long id) {
        ConfigParam config = configParamRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Config param not found with id: " + id));
        return configParamMapper.toDTO(config);
    }
    
    public ConfigParamDTO getConfigParamByKey(String paramKey) {
        ConfigParam config = configParamRepository.findByParamKey(paramKey)
                .orElseThrow(() -> new ResourceNotFoundException("Config param not found with key: " + paramKey));
        return configParamMapper.toDTO(config);
    }
    
    public List<ConfigParamDTO> searchConfigParams(String paramKey) {
        List<ConfigParam> configs = configParamRepository.findByParamKeyContainingIgnoreCase(paramKey);
        return configs.stream()
                .map(configParamMapper::toDTO)
                .collect(Collectors.toList());
    }
    
    @Transactional
    public ConfigParamDTO createConfigParam(ConfigParamDTO dto) {
        // Check if param key already exists
        if (dto.getParamKey() != null && configParamRepository.existsByParamKey(dto.getParamKey())) {
            throw new BusinessException("Config param with key " + dto.getParamKey() + " already exists");
        }
        
        ConfigParam config = configParamMapper.toEntity(dto);
        ConfigParam saved = configParamRepository.save(config);
        log.info("Created config param with id: {}", saved.getId());
        return configParamMapper.toDTO(saved);
    }
    
    @Transactional
    public ConfigParamDTO updateConfigParam(Long id, ConfigParamDTO dto) {
        ConfigParam existing = configParamRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Config param not found with id: " + id));
        
        // Check if new param key conflicts with existing
        if (dto.getParamKey() != null && !dto.getParamKey().equals(existing.getParamKey())) {
            if (configParamRepository.existsByParamKey(dto.getParamKey())) {
                throw new BusinessException("Config param with key " + dto.getParamKey() + " already exists");
            }
        }
        
        configParamMapper.updateEntityFromDTO(dto, existing);
        ConfigParam updated = configParamRepository.save(existing);
        log.info("Updated config param with id: {}", updated.getId());
        return configParamMapper.toDTO(updated);
    }
    
    @Transactional
    public void deleteConfigParam(Long id) {
        ConfigParam config = configParamRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Config param not found with id: " + id));
        
        configParamRepository.delete(config);
        log.info("Deleted config param with id: {}", id);
    }
}
