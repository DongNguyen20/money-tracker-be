package com.kop.api.controller;

import com.kop.api.model.dto.RentPaymentDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/rent/payments")
@RequiredArgsConstructor
public class RentController {
    
    // Placeholder for rent service - will implement with RentService
    
    @GetMapping
    public ResponseEntity<?> getRentPayments(
            @RequestParam(required = false) Integer month,
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) String status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        // TODO: Implement with RentService
        return ResponseEntity.ok(java.util.Collections.emptyList());
    }
    
    @PostMapping
    public ResponseEntity<RentPaymentDTO> createRentPayment(@Valid @RequestBody RentPaymentDTO dto) {
        // TODO: Implement with RentService
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<RentPaymentDTO> updateRentPayment(
            @PathVariable Long id,
            @Valid @RequestBody RentPaymentDTO dto) {
        // TODO: Implement with RentService
        return ResponseEntity.ok(dto);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRentPayment(@PathVariable Long id) {
        // TODO: Implement with RentService
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/summary")
    public ResponseEntity<?> getRentSummary(@RequestParam(required = false) Integer year) {
        // TODO: Implement with RentService
        Map<String, Object> summary = Map.of(
            "totalPaid", BigDecimal.ZERO,
            "totalPending", BigDecimal.ZERO,
            "totalOverdue", BigDecimal.ZERO,
            "averagePayment", BigDecimal.ZERO
        );
        return ResponseEntity.ok(summary);
    }
}