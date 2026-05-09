package com.expensetracker.dashboard.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CategoryTotalResponse {

    private String category;

    private Double totalAmount;
}