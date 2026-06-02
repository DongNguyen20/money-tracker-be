package com.kop.api.controller;

import com.kop.api.model.dto.ConfigParamDTO;
import com.kop.api.service.ConfigParamService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/config-params")
@RequiredArgsConstructor
@Tag(name = "Config Params", description = "Configuration parameter management APIs")
public class ConfigParamController {
    
    private final ConfigParamService configParamService;
    
    @GetMapping
    @Operation(summary = "Get all config params", description = "Retrieve all configuration parameters")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved config params")
    public ResponseEntity<List<ConfigParamDTO>> getAllConfigParams() {
        List<ConfigParamDTO> configs = configParamService.getAllConfigParams();
        return ResponseEntity.ok(configs);
    }
    
    @GetMapping("/search")
    @Operation(summary = "Search config params", description = "Search configuration parameters by key")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved matching config params")
    public ResponseEntity<List<ConfigParamDTO>> searchConfigParams(
            @RequestParam String paramKey) {
        List<ConfigParamDTO> configs = configParamService.searchConfigParams(paramKey);
        return ResponseEntity.ok(configs);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Get config param by ID", description = "Retrieve a specific configuration parameter by ID")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved config param")
    public ResponseEntity<ConfigParamDTO> getConfigParamById(@PathVariable Long id) {
        ConfigParamDTO config = configParamService.getConfigParamById(id);
        return ResponseEntity.ok(config);
    }
    
    @GetMapping("/key/{paramKey}")
    @Operation(summary = "Get config param by key", description = "Retrieve a specific configuration parameter by key")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved config param")
    public ResponseEntity<ConfigParamDTO> getConfigParamByKey(@PathVariable String paramKey) {
        ConfigParamDTO config = configParamService.getConfigParamByKey(paramKey);
        return ResponseEntity.ok(config);
    }
    
    @PostMapping
    @Operation(summary = "Create config param", description = "Create a new configuration parameter")
    @ApiResponse(responseCode = "201", description = "Successfully created config param")
    public ResponseEntity<ConfigParamDTO> createConfigParam(@Valid @RequestBody ConfigParamDTO dto) {
        ConfigParamDTO created = configParamService.createConfigParam(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Update config param", description = "Update an existing configuration parameter")
    @ApiResponse(responseCode = "200", description = "Successfully updated config param")
    public ResponseEntity<ConfigParamDTO> updateConfigParam(
            @PathVariable Long id,
            @Valid @RequestBody ConfigParamDTO dto) {
        ConfigParamDTO updated = configParamService.updateConfigParam(id, dto);
        return ResponseEntity.ok(updated);
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete config param", description = "Delete a configuration parameter")
    @ApiResponse(responseCode = "204", description = "Successfully deleted config param")
    public ResponseEntity<Void> deleteConfigParam(@PathVariable Long id) {
        configParamService.deleteConfigParam(id);
        return ResponseEntity.noContent().build();
    }
}
