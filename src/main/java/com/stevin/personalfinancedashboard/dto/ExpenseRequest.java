package com.stevin.personalfinancedashboard.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExpenseRequest {

    private BigDecimal amount;
    private String description;
    private LocalDate date;
    private Long categoryId;
}