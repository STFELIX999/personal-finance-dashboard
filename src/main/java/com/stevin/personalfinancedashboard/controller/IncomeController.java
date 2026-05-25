package com.stevin.personalfinancedashboard.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.stevin.personalfinancedashboard.entity.Income;
import com.stevin.personalfinancedashboard.service.IncomeService;

@RestController
@RequestMapping("/incomes")
public class IncomeController {

    private final IncomeService incomeService;

    public IncomeController(
            IncomeService incomeService) {
        this.incomeService = incomeService;
    }

    @PostMapping
    public Income createIncome(
            @RequestBody Income income) {

        return incomeService.saveIncome(income);
    }

    @GetMapping
    public List<Income> getAllIncomes() {
        return incomeService.getAllIncomes();
    }
}