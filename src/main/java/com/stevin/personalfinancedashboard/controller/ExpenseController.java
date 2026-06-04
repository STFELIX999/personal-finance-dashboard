package com.stevin.personalfinancedashboard.controller;

import com.stevin.personalfinancedashboard.dto.ExpenseRequest;
import com.stevin.personalfinancedashboard.entity.Expense;
import com.stevin.personalfinancedashboard.service.ExpenseService;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
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

    @GetMapping("/paged")
    public Page<Expense> getExpensesPaged(

            @RequestParam(defaultValue = "0")
            int page,

            @RequestParam(defaultValue = "5")
            int size) {

        return expenseService.getExpenses(page, size);
    }

    @GetMapping("/sorted")
    public List<Expense> getSortedExpenses(

            @RequestParam(defaultValue = "date")
            String sortBy) {

        return expenseService
                .getExpensesSorted(sortBy);
    }

    @GetMapping("/sorted-desc")
    public List<Expense> getSortedExpensesDesc(

            @RequestParam String sortBy) {

        return expenseService
                .getExpensesSortedDesc(sortBy);
    }

    @GetMapping("/category/{categoryId}")
    public List<Expense> getExpensesByCategory(

            @PathVariable Long categoryId) {

        return expenseService
                .getExpensesByCategory(categoryId);
    }

    @GetMapping("/date-range")
    public List<Expense> getExpensesByDateRange(
            @RequestParam
            @DateTimeFormat(
                    iso = DateTimeFormat.ISO.DATE)
            LocalDate startDate,

            @RequestParam
            @DateTimeFormat(
                    iso = DateTimeFormat.ISO.DATE)
            LocalDate endDate) {

        return expenseService.getExpensesByDateRange(startDate, endDate);
    }
}
