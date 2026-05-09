package com.expensetracker.expense.service;

import com.expensetracker.expense.dto.ExpenseRequest;
import com.expensetracker.expense.dto.ExpenseResponse;
import com.expensetracker.expense.entity.Expense;
import com.expensetracker.expense.repository.ExpenseRepository;
import com.expensetracker.user.entity.User;
import com.expensetracker.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExpenseService {

    private final ExpenseRepository expenseRepository;

    private final UserRepository userRepository;

    // Add Expense
    public ExpenseResponse addExpense(
            ExpenseRequest request,
            String email
    ) {

        // Get logged-in user
        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                "User not found"
                        )
                );

        // Create expense
        Expense expense = new Expense();

        expense.setTitle(request.getTitle());
        expense.setAmount(request.getAmount());
        expense.setCategory(request.getCategory());
        expense.setDescription(request.getDescription());
        expense.setExpenseDate(request.getExpenseDate());

        // Attach user
        expense.setUser(user);

        // Save expense
        Expense savedExpense =
                expenseRepository.save(expense);

        // Return response DTO
        return mapToResponse(savedExpense);
    }

    // Get all expenses of logged-in user
    public List<ExpenseResponse> getAllExpenses(
            String email
    ) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                "User not found"
                        )
                );

        List<Expense> expenses =
                expenseRepository.findByUser(user);

        return expenses.stream()
                .map(this::mapToResponse)
                .toList();
    }

    // Convert Entity -> DTO
    private ExpenseResponse mapToResponse(
            Expense expense
    ) {

        return new ExpenseResponse(
                expense.getId(),
                expense.getTitle(),
                expense.getAmount(),
                expense.getCategory(),
                expense.getDescription(),
                expense.getExpenseDate()
        );
    }
}