package com.kop.api.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionStatisticsDTO {
    
    private BigDecimal totalIncome;
    private BigDecimal totalExpense;
    private BigDecimal totalSaving;
    private BigDecimal balance;
    private Long transactionCount;
}