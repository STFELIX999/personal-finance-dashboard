package com.stevin.personalfinancedashboard.service;

import java.util.List;

import com.stevin.personalfinancedashboard.dto.IncomeRequest;
import com.stevin.personalfinancedashboard.entity.Income;

public interface IncomeService {

    Income saveIncome(IncomeRequest request);

    List<Income> getAllIncomes();
}