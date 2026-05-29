package com.stevin.personalfinancedashboard.service.impl;

import java.util.List;

import com.stevin.personalfinancedashboard.dto.IncomeRequest;
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
    public Income saveIncome(IncomeRequest request) {

        Income income = new Income();

        income.setAmount(request.getAmount());
        income.setSource(request.getSource());
        income.setDate(request.getDate());

        return incomeRepository.save(income);
    }

    @Override
    public List<Income> getAllIncomes() {
        return incomeRepository.findAll();
    }
}