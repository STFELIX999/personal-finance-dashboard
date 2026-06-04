package com.stevin.personalfinancedashboard.service.impl;

import java.math.BigDecimal;
import java.util.List;

import com.stevin.personalfinancedashboard.dto.IncomeRequest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    @Override
    public Page<Income> getIncomes(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        return incomeRepository.findAll(pageable);
    }

    @Override
    public List<Income> getIncomesBySource(String source) {
        return incomeRepository.findBySource(source);

    }

    @Override
    public BigDecimal getTotalIncome() {
        return incomeRepository.getTotalIncome();
    }
    @Override
    public BigDecimal getTotalIncomeBySource(String source) {

        return incomeRepository.getTotalIncomeBySource(source);
    }
}