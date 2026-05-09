package com.expensetracker.dashboard.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class DashboardSummaryResponse {

    private Double totalExpenses;

    private Double highestExpense;

    private Long totalTransactions;
}