package com.stevin.personalfinancedashboard.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.stevin.personalfinancedashboard.entity.Income;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;

public interface IncomeRepository
        extends JpaRepository<Income, Long> {

    List<Income> findBySource(String source);

    @Query("""
       SELECT COALESCE(SUM(i.amount), 0)
       FROM Income i
       """)
    BigDecimal getTotalIncome();

    @Query("""
       SELECT COALESCE(SUM(i.amount), 0)
       FROM Income i
       WHERE i.source = :source
       """)
    BigDecimal getTotalIncomeBySource(String source);

}