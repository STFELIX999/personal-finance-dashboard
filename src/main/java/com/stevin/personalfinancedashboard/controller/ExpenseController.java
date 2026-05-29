package com.stevin.personalfinancedashboard.controller;

import com.stevin.personalfinancedashboard.dto.ExpenseRequest;
import com.stevin.personalfinancedashboard.entity.Expense;
import com.stevin.personalfinancedashboard.service.ExpenseService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/expenses")
public class ExpenseController {

    private final ExpenseService expenseService;

    public ExpenseController(
            ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @PostMapping
    public Expense createExpense(
            @Valid
            @RequestBody ExpenseRequest request) {

        return expenseService.createExpense(request);
    }

    @GetMapping
    public List<Expense> getAllExpenses() {
        return expenseService.getAllExpenses();
    }

    @PutMapping("/{id}")
    public Expense updateExpense(
            @PathVariable Long id,
            @RequestBody ExpenseRequest request) {

        return expenseService
                .updateExpense(id, request);
    }

    @DeleteMapping("/{id}")
    public String deleteExpense(
            @PathVariable Long id) {

        expenseService.deleteExpense(id);

        return "Expense deleted successfully";
    }
}
