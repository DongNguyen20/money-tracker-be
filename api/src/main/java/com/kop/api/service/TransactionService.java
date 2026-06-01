package com.kop.api.service;

import com.kop.api.exception.ResourceNotFoundException;
import com.kop.api.model.dto.PageResponseDTO;
import com.kop.api.model.dto.TransactionDTO;
import com.kop.api.model.dto.TransactionStatisticsDTO;
import com.kop.api.model.entity.Transaction;
import com.kop.api.repository.TransactionRepository;
import com.kop.api.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionService {
    
    private final TransactionRepository transactionRepository;
    private final CategoryRepository categoryRepository;
    
    public PageResponseDTO<TransactionDTO> getAllTransactions(
            Transaction.TransactionType type,
            String categoryId,
            LocalDate dateFrom,
            LocalDate dateTo,
            int page,
            int size,
            String sortField,
            String sortDirection) {
        
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortField);
        Pageable pageable = PageRequest.of(page, size, sort);
        
        Page<Transaction> transactions = transactionRepository.findWithFilters(
                type, categoryId, dateFrom, dateTo, pageable);
        
        List<TransactionDTO> dtos = transactions.getContent().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        
        return PageResponseDTO.of(dtos, transactions.getTotalElements(), page, size);
    }
    
    public TransactionDTO getTransactionById(Long id) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction not found with id: " + id));
        return convertToDTO(transaction);
    }
    
    @Transactional
    public TransactionDTO createTransaction(TransactionDTO dto) {
        // Validate category exists
        categoryRepository.findByCategoryId(dto.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + dto.getCategoryId()));
        
        Transaction transaction = convertToEntity(dto);
        Transaction saved = transactionRepository.save(transaction);
        log.info("Created transaction with id: {}", saved.getId());
        return convertToDTO(saved);
    }
    
    @Transactional
    public TransactionDTO updateTransaction(Long id, TransactionDTO dto) {
        Transaction existing = transactionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction not found with id: " + id));
        
        // Validate category exists if changed
        if (!existing.getCategoryId().equals(dto.getCategoryId())) {
            categoryRepository.findByCategoryId(dto.getCategoryId())
                    .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + dto.getCategoryId()));
        }
        
        // Update fields
        existing.setAmount(dto.getAmount());
        existing.setType(dto.getType());
        existing.setCategoryId(dto.getCategoryId());
        existing.setNote(dto.getNote());
        existing.setDate(dto.getDate());
        
        Transaction updated = transactionRepository.save(existing);
        log.info("Updated transaction with id: {}", updated.getId());
        return convertToDTO(updated);
    }
    
    @Transactional
    public void deleteTransaction(Long id) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction not found with id: " + id));
        
        transactionRepository.delete(transaction);
        log.info("Deleted transaction with id: {}", id);
    }
    
    public TransactionStatisticsDTO getStatistics(Integer month, Integer year) {
        BigDecimal totalIncome = transactionRepository.sumByTypeAndPeriod(
                Transaction.TransactionType.INCOME, month, year);
        BigDecimal totalExpense = transactionRepository.sumByTypeAndPeriod(
                Transaction.TransactionType.EXPENSE, month, year);
        BigDecimal totalSaving = transactionRepository.sumByTypeAndPeriod(
                Transaction.TransactionType.SAVING, month, year);
        
        BigDecimal balance = totalIncome.subtract(totalExpense).subtract(totalSaving);
        
        long transactionCount = transactionRepository.count();
        
        return TransactionStatisticsDTO.builder()
                .totalIncome(totalIncome != null ? totalIncome : BigDecimal.ZERO)
                .totalExpense(totalExpense != null ? totalExpense : BigDecimal.ZERO)
                .totalSaving(totalSaving != null ? totalSaving : BigDecimal.ZERO)
                .balance(balance)
                .transactionCount(transactionCount)
                .build();
    }
    
    public List<TransactionDTO> getRecentTransactions(int limit) {
        Pageable pageable = PageRequest.of(0, limit, Sort.by(Sort.Direction.DESC, "date", "createdAt"));
        List<Transaction> transactions = transactionRepository.findRecentTransactions(pageable);
        return transactions.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    private TransactionDTO convertToDTO(Transaction transaction) {
        return TransactionDTO.builder()
                .id(transaction.getId())
                .amount(transaction.getAmount())
                .type(transaction.getType())
                .categoryId(transaction.getCategoryId())
                .note(transaction.getNote())
                .date(transaction.getDate())
                .createdAt(transaction.getCreatedAt())
                .updatedAt(transaction.getUpdatedAt())
                .build();
    }
    
    private Transaction convertToEntity(TransactionDTO dto) {
        return Transaction.builder()
                .amount(dto.getAmount())
                .type(dto.getType())
                .categoryId(dto.getCategoryId())
                .note(dto.getNote())
                .date(dto.getDate())
                .build();
    }
}