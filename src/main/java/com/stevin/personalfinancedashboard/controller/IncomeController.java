package com.stevin.personalfinancedashboard.controller;

import java.math.BigDecimal;
import java.util.List;

import com.stevin.personalfinancedashboard.dto.IncomeRequest;

import com.stevin.personalfinancedashboard.dto.IncomeSummaryResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
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

    @GetMapping("/paged")
    public Page<Income> getExpensesPaged(

            @RequestParam(defaultValue = "0")
            int page,

            @RequestParam(defaultValue = "5")
            int size) {

        return incomeService.getIncomes(page, size);
    }

    @GetMapping("/source/{source}")
    public List<Income> getIncomesBySource(
            @PathVariable String source) {
        return incomeService.getIncomesBySource(source);

    }

    @GetMapping("/total")
    public IncomeSummaryResponse getTotalIncome() {

        BigDecimal total = incomeService.getTotalIncome();

        return new IncomeSummaryResponse(total);
    }

    @GetMapping("/source/{source}/total")
    public IncomeSummaryResponse getIncomeBySourceTotal(
            @PathVariable String source) {

        return new IncomeSummaryResponse(
                incomeService.getTotalIncomeBySource(source));
    }
}