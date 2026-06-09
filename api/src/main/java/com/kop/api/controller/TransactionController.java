package com.kop.api.controller;

import com.kop.api.model.dto.TransactionDTO;
import com.kop.api.model.entity.Transaction;
import com.kop.api.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/v1/transactions")
@RequiredArgsConstructor
@Tag(name = "Transactions", description = "Transaction management APIs")
@CrossOrigin(origins = "http://localhost:3000")
public class TransactionController {
    
    private final TransactionService transactionService;
    
    @GetMapping
    @Operation(summary = "Get all transactions with filters", description = "Retrieve transactions with optional filtering by type, category, and date range")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved transactions")
    public ResponseEntity<Page<TransactionDTO>> getAllTransactions(
            @Parameter(description = "Filter by transaction type (INCOME, EXPENSE, SAVING)")
            @RequestParam(required = false) Transaction.TransactionType type,
            @Parameter(description = "Filter by category ID")
            @RequestParam(required = false) String categoryId,
            @Parameter(description = "Filter by start date")
            @RequestParam(required = false) LocalDate dateFrom,
            @Parameter(description = "Filter by end date")
            @RequestParam(required = false) LocalDate dateTo,
            @Parameter(description = "Page number (default: 1)")
            @RequestParam(defaultValue = "1") int page,
            @Parameter(description = "Page size (default: 10)")
            @RequestParam(defaultValue = "10") int size) {
        Page<TransactionDTO> transactions = transactionService.getAllTransactions(type, categoryId, dateFrom, dateTo, page, size);
        return ResponseEntity.ok(transactions);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Get transaction by ID", description = "Retrieve a specific transaction by ID")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved transaction")
    @ApiResponse(responseCode = "404", description = "Transaction not found")
    public ResponseEntity<TransactionDTO> getTransactionById(@PathVariable Long id) {
        TransactionDTO transaction = transactionService.getTransactionById(id);
        return ResponseEntity.ok(transaction);
    }
    
    @PostMapping
    @Operation(summary = "Create transaction", description = "Create a new transaction")
    @ApiResponse(responseCode = "201", description = "Successfully created transaction")
    public ResponseEntity<TransactionDTO> createTransaction(@Valid @RequestBody TransactionDTO dto) {
        TransactionDTO created = transactionService.createTransaction(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Update transaction", description = "Update an existing transaction")
    @ApiResponse(responseCode = "200", description = "Successfully updated transaction")
    @ApiResponse(responseCode = "404", description = "Transaction not found")
    public ResponseEntity<TransactionDTO> updateTransaction(
            @PathVariable Long id,
            @Valid @RequestBody TransactionDTO dto) {
        TransactionDTO updated = transactionService.updateTransaction(id, dto);
        return ResponseEntity.ok(updated);
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete transaction", description = "Delete a transaction")
    @ApiResponse(responseCode = "204", description = "Successfully deleted transaction")
    @ApiResponse(responseCode = "404", description = "Transaction not found")
    public ResponseEntity<Void> deleteTransaction(@PathVariable Long id) {
        transactionService.deleteTransaction(id);
        return ResponseEntity.noContent().build();
    }
}