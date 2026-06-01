package com.stevin.personalfinancedashboard.service;

import java.util.List;

import com.stevin.personalfinancedashboard.dto.ExpenseRequest;
import com.stevin.personalfinancedashboard.entity.Expense;
import org.springframework.data.domain.Page;

public interface ExpenseService {

    Expense createExpense(ExpenseRequest request);

    List<Expense> getAllExpenses();

    Expense updateExpense(Long id, ExpenseRequest request);

    void deleteExpense(Long id);

    Page<Expense> getExpenses(int page, int size);
}