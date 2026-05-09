package com.expensetracker.expense.controller;

import com.expensetracker.expense.dto.ExpenseRequest;
import com.expensetracker.expense.dto.ExpenseResponse;
import com.expensetracker.expense.service.ExpenseService;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/expenses")
@RequiredArgsConstructor
public class ExpenseController {

    private final ExpenseService expenseService;

    // Add expense
    @PostMapping
    public ExpenseResponse addExpense(
            @Valid @RequestBody ExpenseRequest request,
            Authentication authentication
    ) {

        // Get logged-in user email
        String email = authentication.getName();

        return expenseService.addExpense(
                request,
                email
        );
    }

    // Get all expenses
    @GetMapping
    public List<ExpenseResponse> getAllExpenses(
            Authentication authentication
    ) {

        // Get logged-in user email
        String email = authentication.getName();

        return expenseService.getAllExpenses(email);
    }
}