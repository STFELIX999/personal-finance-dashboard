package com.stevin.personalfinancedashboard.service.impl;

import java.time.LocalDate;
import java.util.List;

import com.stevin.personalfinancedashboard.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import com.stevin.personalfinancedashboard.dto.ExpenseRequest;
import com.stevin.personalfinancedashboard.entity.Category;
import com.stevin.personalfinancedashboard.entity.Expense;
import com.stevin.personalfinancedashboard.repository.CategoryRepository;
import com.stevin.personalfinancedashboard.repository.ExpenseRepository;
import com.stevin.personalfinancedashboard.service.ExpenseService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import org.springframework.data.domain.Sort;

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
                        .orElseThrow(() ->
                                new ResourceNotFoundException("Category not found with id: "
                                        + request.getCategoryId()));

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

    @Override
    public Expense updateExpense(Long id,
                                 ExpenseRequest request) {

        Expense expense = expenseRepository
                .findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Expense not found"));

        Category category = categoryRepository
                .findById(request.getCategoryId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Category not found"));

        expense.setAmount(request.getAmount());
        expense.setDescription(request.getDescription());
        expense.setDate(request.getDate());
        expense.setCategory(category);

        return expenseRepository.save(expense);
    }

    @Override
    public void deleteExpense(Long id) {

        Expense expense = expenseRepository
                .findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Expense not found"));

        expenseRepository.delete(expense);
    }

    @Override
    public Page<Expense> getExpenses(int page, int size) {

        Pageable pageable = PageRequest.of(page, size);

        return expenseRepository.findAll(pageable);
    }
    @Override
    public List<Expense> getExpensesSorted(String sortBy) {

        return expenseRepository.findAll(
                Sort.by(sortBy));
    }

    @Override
    public List<Expense> getExpensesSortedDesc(
            String sortBy) {

        return expenseRepository.findAll(
                Sort.by(sortBy).descending());
    }

    @Override
    public List<Expense> getExpensesByCategory(
            Long categoryId) {

        return expenseRepository
                .findByCategoryId(categoryId);
    }

    @Override
    public List<Expense> getExpensesByDateRange(
            LocalDate startDate, LocalDate endDate) {

        return expenseRepository.findByDateBetween(startDate, endDate);
    }

}