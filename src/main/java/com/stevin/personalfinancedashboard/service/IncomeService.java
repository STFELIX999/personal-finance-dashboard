package com.stevin.personalfinancedashboard.service;

import java.math.BigDecimal;
import java.util.List;

import com.stevin.personalfinancedashboard.dto.IncomeRequest;
import com.stevin.personalfinancedashboard.entity.Income;
import org.springframework.data.domain.Page;

public interface IncomeService {

    Income saveIncome(IncomeRequest request);

    List<Income> getAllIncomes();

    Page<Income> getIncomes(int page, int size);

    List<Income> getIncomesBySource(String source);

    BigDecimal getTotalIncome();

    BigDecimal getTotalIncomeBySource(String source);
}