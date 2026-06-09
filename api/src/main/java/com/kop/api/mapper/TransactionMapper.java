package com.kop.api.mapper;

import com.kop.api.model.dto.TransactionDTO;
import com.kop.api.model.entity.Transaction;
import org.springframework.stereotype.Component;

@Component
public class TransactionMapper {
    
    public TransactionDTO toDTO(Transaction transaction) {
        if (transaction == null) {
            return null;
        }
        
        TransactionDTO dto = TransactionDTO.builder()
                .id(transaction.getId())
                .amount(transaction.getAmount())
                .type(transaction.getType())
                .categoryId(transaction.getCategoryId().toString())
                .note(transaction.getNote())
                .date(transaction.getDate())
                .categoryName(null) // Will be set by service if needed
                .categoryIcon(null)
                .categoryColor(null)
                .createdAt(transaction.getCreatedAt())
                .updatedAt(transaction.getUpdatedAt())
                .build();
        return dto;
    }
    
    public Transaction toEntity(TransactionDTO dto) {
        if (dto == null) {
            return null;
        }
        
        Transaction transaction = Transaction.builder()
                .amount(dto.getAmount())
                .type(dto.getType())
                .categoryId(Long.parseLong(dto.getCategoryId()))
                .note(dto.getNote())
                .date(dto.getDate())
                .build();
        return transaction;
    }
    
    public void updateEntityFromDTO(TransactionDTO dto, Transaction transaction) {
        if (dto == null || transaction == null) {
            return;
        }
        
        if (dto.getAmount() != null) {
            transaction.setAmount(dto.getAmount());
        }
        if (dto.getType() != null) {
            transaction.setType(dto.getType());
        }
        if (dto.getCategoryId() != null) {
            transaction.setCategoryId(Long.parseLong(dto.getCategoryId()));
        }
        if (dto.getNote() != null) {
            transaction.setNote(dto.getNote());
        }
        if (dto.getDate() != null) {
            transaction.setDate(dto.getDate());
        }
    }
}