package com.kop.api.controller;

import com.kop.api.dto.PageResponseDTO;
import com.kop.api.dto.TransactionDTO;
import com.kop.api.dto.TransactionStatisticsDTO;
import com.kop.api.model.entity.Transaction;
import com.kop.api.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/transactions")
@RequiredArgsConstructor
@Tag(name = "Transactions", description = "Transaction management APIs")
@SecurityRequirement(name = "X-API-Key")
public class TransactionController {
    
    private final TransactionService transactionService;
    
    @GetMapping
    @Operation(summary = "Get all transactions", description = "Retrieve paginated list of transactions with optional filters")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved transactions", 
                content = @Content(schema = @Schema(implementation = PageResponseDTO.class))),
        @ApiResponse(responseCode = "401", description = "Unauthorized - Invalid API key"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<PageResponseDTO<TransactionDTO>> getAllTransactions(
            @RequestParam(required = false) Transaction.TransactionType type,
            @RequestParam(required = false) String categoryId,
            @RequestParam(required = false) LocalDate dateFrom,
            @RequestParam(required = false) LocalDate dateTo,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "date") String sortField,
            @RequestParam(defaultValue = "desc") String sortDirection) {
        
        PageResponseDTO<TransactionDTO> response = transactionService.getAllTransactions(
                type, categoryId, dateFrom, dateTo, page, size, sortField, sortDirection);
        
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<TransactionDTO> getTransactionById(@PathVariable Long id) {
        TransactionDTO transaction = transactionService.getTransactionById(id);
        return ResponseEntity.ok(transaction);
    }
    
    @PostMapping
    @Operation(summary = "Create new transaction", description = "Create a new transaction with validation")
    @ApiResponse(responseCode = "201", description = "Transaction created successfully")
    @ApiResponse(responseCode = "400", description = "Invalid request data")
    public ResponseEntity<TransactionDTO> createTransaction(@Valid @RequestBody TransactionDTO dto) {
        TransactionDTO created = transactionService.createTransaction(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<TransactionDTO> updateTransaction(
            @PathVariable Long id,
            @Valid @RequestBody TransactionDTO dto) {
        TransactionDTO updated = transactionService.updateTransaction(id, dto);
        return ResponseEntity.ok(updated);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable Long id) {
        transactionService.deleteTransaction(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/statistics")
    public ResponseEntity<TransactionStatisticsDTO> getStatistics(
            @RequestParam(required = false) Integer month,
            @RequestParam(required = false) Integer year) {
        TransactionStatisticsDTO statistics = transactionService.getStatistics(month, year);
        return ResponseEntity.ok(statistics);
    }
    
    @GetMapping("/recent")
    public ResponseEntity<List<TransactionDTO>> getRecentTransactions(
            @RequestParam(defaultValue = "5") int limit) {
        List<TransactionDTO> transactions = transactionService.getRecentTransactions(limit);
        return ResponseEntity.ok(transactions);
    }
}
