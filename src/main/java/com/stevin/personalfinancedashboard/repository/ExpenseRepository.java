package com.stevin.personalfinancedashboard.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.stevin.personalfinancedashboard.entity.Expense;

import java.time.LocalDate;
import java.util.List;

import java.math.BigDecimal;
import org.springframework.data.jpa.repository.Query;

public interface ExpenseRepository
        extends JpaRepository<Expense, Long> {

    List<Expense> findByCategoryId(Long categoryId);

    List<Expense> findByDateBetween(LocalDate startDate, LocalDate endDate);

    @Query("""
       SELECT COALESCE(SUM(e.amount), 0)
       FROM Expense e
       """)
    BigDecimal getTotalExpenses();

    @Query("""
       SELECT COALESCE(SUM(e.amount), 0)
       FROM Expense e
       WHERE e.category.id = :categoryId
       """)
    BigDecimal getTotalExpensesByCategory(Long categoryId);

}