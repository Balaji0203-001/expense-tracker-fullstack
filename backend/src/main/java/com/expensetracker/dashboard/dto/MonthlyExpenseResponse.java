package com.expensetracker.dashboard.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MonthlyExpenseResponse {

    private Integer month;

    private Double totalAmount;
}