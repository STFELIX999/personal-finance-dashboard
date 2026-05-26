package com.stevin.personalfinancedashboard.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.stevin.personalfinancedashboard.dto.ExpenseRequest;
import com.stevin.personalfinancedashboard.entity.Category;
import com.stevin.personalfinancedashboard.entity.Expense;
import com.stevin.personalfinancedashboard.repository.CategoryRepository;
import com.stevin.personalfinancedashboard.repository.ExpenseRepository;
import com.stevin.personalfinancedashboard.service.ExpenseService;

@Service
public class ExpenseServiceImpl implements ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final CategoryRepository categoryRepository;

    public ExpenseServiceImpl(ExpenseRepository expenseRepository,
                              CategoryRepository categoryRepository) {
        this.expenseRepository = expenseRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Expense createExpense(ExpenseRequest request) {

        Category category =
                categoryRepository.findById(
                                request.getCategoryId())
                        .orElseThrow();

        Expense expense = new Expense();

        expense.setAmount(request.getAmount());
        expense.setDescription(request.getDescription());
        expense.setDate(request.getDate());
        expense.setCategory(category);

        return expenseRepository.save(expense);
    }

    @Override
    public List<Expense> getAllExpenses() {
        return expenseRepository.findAll();
    }
}