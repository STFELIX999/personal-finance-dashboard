package com.stevin.personalfinancedashboard.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.stevin.personalfinancedashboard.entity.Expense;

import java.time.LocalDate;
import java.util.List;

public interface ExpenseRepository
        extends JpaRepository<Expense, Long> {

    List<Expense> findByCategoryId(Long categoryId);

    List<Expense> findByDateBetween(LocalDate startDate, LocalDate endDate);

}