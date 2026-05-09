package com.expensetracker.expense.dto;

import com.expensetracker.expense.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class ExpenseResponse {

    private Long id;

    private String title;

    private Double amount;

    private Category category;

    private String description;

    private LocalDate expenseDate;
}