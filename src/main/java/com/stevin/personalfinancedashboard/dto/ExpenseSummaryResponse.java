package com.stevin.personalfinancedashboard.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ExpenseSummaryResponse {

    private BigDecimal totalExpense;
}