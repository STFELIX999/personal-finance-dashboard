package com.stevin.personalfinancedashboard.service.impl;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.stevin.personalfinancedashboard.dto.DashboardSummaryResponse;
import com.stevin.personalfinancedashboard.repository.ExpenseRepository;
import com.stevin.personalfinancedashboard.repository.IncomeRepository;
import com.stevin.personalfinancedashboard.service.DashboardService;

@Service
public class DashboardServiceImpl
        implements DashboardService {

    private final IncomeRepository incomeRepository;
    private final ExpenseRepository expenseRepository;

    public DashboardServiceImpl(
            IncomeRepository incomeRepository,
            ExpenseRepository expenseRepository) {

        this.incomeRepository = incomeRepository;
        this.expenseRepository = expenseRepository;
    }

    @Override
    public DashboardSummaryResponse getSummary() {

        BigDecimal totalIncome = incomeRepository.getTotalIncome();

        BigDecimal totalExpense = expenseRepository.getTotalExpenses();

        BigDecimal balance = totalIncome.subtract(totalExpense);

        return new DashboardSummaryResponse(
                totalIncome,
                totalExpense,
                balance);
    }
}