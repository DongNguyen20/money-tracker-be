package com.kop.api.controller;

import com.kop.api.model.dto.DashboardOverviewDTO;
import com.kop.api.model.dto.TransactionDTO;
import com.kop.api.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/dashboard")
@RequiredArgsConstructor
public class DashboardController {
    
    private final TransactionService transactionService;
    
    @GetMapping("/overview")
    public ResponseEntity<DashboardOverviewDTO> getDashboardOverview(
            @RequestParam(required = false) Integer month,
            @RequestParam(required = false) Integer year) {
        // TODO: Implement full dashboard overview with stock assets
        DashboardOverviewDTO overview = DashboardOverviewDTO.builder()
                .balance(java.math.BigDecimal.ZERO)
                .monthlyIncome(java.math.BigDecimal.ZERO)
                .monthlyExpense(java.math.BigDecimal.ZERO)
                .monthlySaving(java.math.BigDecimal.ZERO)
                .stockAssets(java.math.BigDecimal.ZERO)
                .totalAssets(java.math.BigDecimal.ZERO)
                .build();
        return ResponseEntity.ok(overview);
    }
    
    @GetMapping("/recent-transactions")
    public ResponseEntity<List<TransactionDTO>> getRecentTransactions(
            @RequestParam(defaultValue = "5") int limit) {
        List<TransactionDTO> transactions = transactionService.getRecentTransactions(limit);
        return ResponseEntity.ok(transactions);
    }
    
    @GetMapping("/top-spending")
    public ResponseEntity<?> getTopSpending(
            @RequestParam(required = false) Integer month,
            @RequestParam(required = false) Integer year,
            @RequestParam(defaultValue = "5") int limit) {
        // TODO: Implement top spending by category
        return ResponseEntity.ok(java.util.Collections.emptyList());
    }
    
    @GetMapping("/daily-trend")
    public ResponseEntity<?> getDailyTrend() {
        // TODO: Implement daily trend for last 7 days
        return ResponseEntity.ok(java.util.Collections.emptyList());
    }
}