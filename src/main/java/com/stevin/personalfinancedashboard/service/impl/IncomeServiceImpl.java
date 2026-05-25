package com.stevin.personalfinancedashboard.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.stevin.personalfinancedashboard.entity.Income;
import com.stevin.personalfinancedashboard.repository.IncomeRepository;
import com.stevin.personalfinancedashboard.service.IncomeService;

@Service
public class IncomeServiceImpl implements IncomeService {

    private final IncomeRepository incomeRepository;

    public IncomeServiceImpl(
            IncomeRepository incomeRepository) {
        this.incomeRepository = incomeRepository;
    }

    @Override
    public Income saveIncome(Income income) {
        return incomeRepository.save(income);
    }

    @Override
    public List<Income> getAllIncomes() {
        return incomeRepository.findAll();
    }
}