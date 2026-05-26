package com.stevin.personalfinancedashboard.service;

import java.util.List;

import com.stevin.personalfinancedashboard.dto.ExpenseRequest;
import com.stevin.personalfinancedashboard.entity.Expense;

public interface ExpenseService {

    Expense createExpense(ExpenseRequest request);

    List<Expense> getAllExpenses();
}