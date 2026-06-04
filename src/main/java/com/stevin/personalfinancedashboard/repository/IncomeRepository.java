package com.stevin.personalfinancedashboard.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.stevin.personalfinancedashboard.entity.Income;

import java.util.List;

public interface IncomeRepository
        extends JpaRepository<Income, Long> {

    List<Income> findBySource(String source);

}