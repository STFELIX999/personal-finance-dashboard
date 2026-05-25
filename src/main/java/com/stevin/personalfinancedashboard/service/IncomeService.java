package com.stevin.personalfinancedashboard.service;

import java.util.List;

import com.stevin.personalfinancedashboard.entity.Income;

public interface IncomeService {

    Income saveIncome(Income income);

    List<Income> getAllIncomes();
}