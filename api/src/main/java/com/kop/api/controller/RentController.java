package com.kop.api.controller;

import com.kop.api.model.dto.RentDTO;
import com.kop.api.model.entity.Rent;
import com.kop.api.service.RentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/rents")
@RequiredArgsConstructor
@Tag(name = "Rents", description = "Rent management APIs")
@CrossOrigin(origins = "http://localhost:3000")
public class RentController {
    
    private final RentService rentService;
    
    @GetMapping
    @Operation(summary = "Get all rents", description = "Retrieve all rent records")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved rents")
    public ResponseEntity<List<RentDTO>> getAllRents() {
        List<RentDTO> rents = rentService.getAllRents();
        return ResponseEntity.ok(rents);
    }
    
    @GetMapping("/status/{status}")
    @Operation(summary = "Get rents by status", description = "Retrieve rent records by status")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved rents by status")
    public ResponseEntity<List<RentDTO>> getRentsByStatus(@PathVariable Rent.RentStatus status) {
        List<RentDTO> rents = rentService.getRentsByStatus(status);
        return ResponseEntity.ok(rents);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Get rent by ID", description = "Retrieve a specific rent record by ID")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved rent")
    public ResponseEntity<RentDTO> getRentById(@PathVariable Long id) {
        RentDTO rent = rentService.getRentById(id);
        return ResponseEntity.ok(rent);
    }
    
    @PostMapping
    @Operation(summary = "Create rent", description = "Create a new rent record")
    @ApiResponse(responseCode = "201", description = "Successfully created rent")
    public ResponseEntity<RentDTO> createRent(@Valid @RequestBody RentDTO dto) {
        RentDTO created = rentService.createRent(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Update rent", description = "Update an existing rent record")
    @ApiResponse(responseCode = "200", description = "Successfully updated rent")
    public ResponseEntity<RentDTO> updateRent(
            @PathVariable Long id,
            @Valid @RequestBody RentDTO dto) {
        RentDTO updated = rentService.updateRent(id, dto);
        return ResponseEntity.ok(updated);
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete rent", description = "Delete a rent record")
    @ApiResponse(responseCode = "204", description = "Successfully deleted rent")
    public ResponseEntity<Void> deleteRent(@PathVariable Long id) {
        rentService.deleteRent(id);
        return ResponseEntity.noContent().build();
    }
    
    @PatchMapping("/{id}/status")
    @Operation(summary = "Update rent status", description = "Update the status of a rent record")
    @ApiResponse(responseCode = "200", description = "Successfully updated rent status")
    public ResponseEntity<RentDTO> updateRentStatus(
            @PathVariable Long id,
            @RequestParam Rent.RentStatus status) {
        RentDTO updated = rentService.updateRentStatus(id, status);
        return ResponseEntity.ok(updated);
    }
}