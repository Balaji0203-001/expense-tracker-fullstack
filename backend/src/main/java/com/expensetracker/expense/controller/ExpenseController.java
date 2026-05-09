package com.expensetracker.expense.controller;

import com.expensetracker.expense.dto.ExpenseRequest;
import com.expensetracker.expense.dto.ExpenseResponse;
import com.expensetracker.expense.entity.Category;
import com.expensetracker.expense.service.ExpenseService;

import org.springframework.data.domain.Page;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
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

    // Get expense by ID
    @GetMapping("/{id}")
    public ExpenseResponse getExpenseById(
            @PathVariable Long id,
            Authentication authentication
    ) {

        // Get logged-in user email
        String email = authentication.getName();

        return expenseService.getExpenseById(
                id,
                email
        );
    }

    // Update expense
    @PutMapping("/{id}")
    public ExpenseResponse updateExpense(
            @PathVariable Long id,
            @Valid @RequestBody ExpenseRequest request,
            Authentication authentication
    ) {

        // Get logged-in user email
        String email = authentication.getName();

        return expenseService.updateExpense(
                id,
                request,
                email
        );
    }

    // Delete expense
    @DeleteMapping("/{id}")
    public String deleteExpense(
            @PathVariable Long id,
            Authentication authentication
    ) {

        // Get logged-in user email
        String email = authentication.getName();

        return expenseService.deleteExpense(
                id,
                email
        );
    }

    // Filter expenses by category
    @GetMapping("/filter/category")
    public List<ExpenseResponse> getExpensesByCategory(
            @RequestParam Category category,
            Authentication authentication
    ) {

        // Get logged-in user email
        String email = authentication.getName();

        return expenseService.getExpensesByCategory(
                category,
                email
        );
    }

    // Filter expenses by date
    @GetMapping("/filter/date")
    public List<ExpenseResponse> getExpensesByDate(
            @RequestParam LocalDate date,
            Authentication authentication
    ) {

        // Get logged-in user email
        String email = authentication.getName();

        return expenseService.getExpensesByDate(
                date,
                email
        );
    }

    // Search expenses by title
    @GetMapping("/search")
    public List<ExpenseResponse> searchExpenses(
            @RequestParam String keyword,
            Authentication authentication
    ) {

        // Get logged-in user email
        String email = authentication.getName();

        return expenseService.searchExpenses(
                keyword,
                email
        );
    }

    // Get paginated expenses
    @GetMapping("/paginated")
    public Page<ExpenseResponse> getPaginatedExpenses(
            @RequestParam int page,
            @RequestParam int size,
            Authentication authentication
    ) {

        // Get logged-in user email
        String email = authentication.getName();

        return expenseService.getPaginatedExpenses(
                page,
                size,
                email
        );
    }

    // Sort expenses
    @GetMapping("/sort")
    public List<ExpenseResponse> sortExpenses(
            @RequestParam String field,
            Authentication authentication
    ) {

        // Get logged-in user email
        String email = authentication.getName();

        return expenseService.sortExpenses(
                field,
                email
        );
    }
}