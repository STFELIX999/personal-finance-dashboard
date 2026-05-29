package com.stevin.personalfinancedashboard.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class IncomeRequest {

    @NotNull(message = "Amount is required")
    private BigDecimal amount;

    private String source;
    private LocalDate date;
}
