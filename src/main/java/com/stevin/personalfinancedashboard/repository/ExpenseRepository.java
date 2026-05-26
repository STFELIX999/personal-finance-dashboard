package com.stevin.personalfinancedashboard.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.stevin.personalfinancedashboard.entity.Expense;

public interface ExpenseRepository
        extends JpaRepository<Expense, Long> {

}