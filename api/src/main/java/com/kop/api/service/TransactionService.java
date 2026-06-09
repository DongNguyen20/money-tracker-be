package com.kop.api.service;

import com.kop.api.exception.ResourceNotFoundException;
import com.kop.api.mapper.TransactionMapper;
import com.kop.api.model.dto.TransactionDTO;
import com.kop.api.model.entity.Transaction;
import com.kop.api.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionService {
    
    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;
    
    public Page<TransactionDTO> getAllTransactions(Transaction.TransactionType type, String categoryId, 
                                                   java.time.LocalDate dateFrom, java.time.LocalDate dateTo,
                                                   int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by("date").descending().and(Sort.by("createdAt").descending()));
        
        // Handle "all" values
        Transaction.TransactionType typeFilter = "all".equals(type) ? null : type;
        String categoryIdFilter = "all".equals(categoryId) ? null : categoryId;
        
        Page<Transaction> transactions = transactionRepository.findWithFilters(
                typeFilter, categoryIdFilter, dateFrom, dateTo, pageable);
        return transactions.map(transactionMapper::toDTO);
    }
    
    public List<TransactionDTO> getRecentTransactions(int limit) {
        Pageable pageable = PageRequest.of(0, limit, Sort.by("date").descending().and(Sort.by("createdAt").descending()));
        List<Transaction> transactions = transactionRepository.findRecentTransactions(pageable);
        return transactions.stream()
                .map(transactionMapper::toDTO)
                .collect(Collectors.toList());
    }
    
    public TransactionDTO getTransactionById(Long id) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction not found with id: " + id));
        return transactionMapper.toDTO(transaction);
    }
    
    @Transactional
    public TransactionDTO createTransaction(TransactionDTO dto) {
        Transaction transaction = transactionMapper.toEntity(dto);
        Transaction savedTransaction = transactionRepository.save(transaction);
        log.info("Created transaction with id: {}", savedTransaction.getId());
        return transactionMapper.toDTO(savedTransaction);
    }
    
    @Transactional
    public TransactionDTO updateTransaction(Long id, TransactionDTO dto) {
        Transaction existing = transactionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction not found with id: " + id));
        
        transactionMapper.updateEntityFromDTO(dto, existing);
        Transaction updated = transactionRepository.save(existing);
        log.info("Updated transaction with id: {}", updated.getId());
        return transactionMapper.toDTO(updated);
    }
    
    @Transactional
    public void deleteTransaction(Long id) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction not found with id: " + id));
        transactionRepository.delete(transaction);
        log.info("Deleted transaction with id: {}", id);
    }
}