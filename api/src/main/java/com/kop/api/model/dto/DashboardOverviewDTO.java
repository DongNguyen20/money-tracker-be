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
public class DashboardOverviewDTO {
    
    private BigDecimal balance;
    private BigDecimal monthlyIncome;
    private BigDecimal monthlyExpense;
    private BigDecimal monthlySaving;
    private BigDecimal stockAssets;
    private BigDecimal totalAssets;
}