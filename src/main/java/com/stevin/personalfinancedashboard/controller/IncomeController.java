package com.stevin.personalfinancedashboard.controller;

import java.util.List;

import com.stevin.personalfinancedashboard.dto.IncomeRequest;
import jakarta.validation.Valid;
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
            @Valid
            @RequestBody IncomeRequest request) {

        return incomeService.saveIncome(request);
    }

    @GetMapping
    public List<Income> getAllIncomes() {
        return incomeService.getAllIncomes();
    }
}