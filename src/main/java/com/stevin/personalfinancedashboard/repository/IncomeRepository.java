package com.stevin.personalfinancedashboard.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.stevin.personalfinancedashboard.entity.Income;

public interface IncomeRepository
        extends JpaRepository<Income, Long> {

}