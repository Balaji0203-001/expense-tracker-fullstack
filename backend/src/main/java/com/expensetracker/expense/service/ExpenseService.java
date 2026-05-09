package com.expensetracker.expense.service;

import com.expensetracker.exception.ExpenseNotFoundException;
import com.expensetracker.expense.dto.ExpenseRequest;
import com.expensetracker.expense.dto.ExpenseResponse;
import com.expensetracker.expense.entity.Category;
import com.expensetracker.expense.entity.Expense;
import com.expensetracker.expense.repository.ExpenseRepository;
import com.expensetracker.user.entity.User;
import com.expensetracker.user.repository.UserRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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

    // Get expense by ID
    public ExpenseResponse getExpenseById(
            Long expenseId,
            String email
    ) {

        // Get logged-in user
        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                "User not found"
                        )
                );

        // Find expense belonging to user
        Expense expense =
                expenseRepository.findByIdAndUser(
                        expenseId,
                        user
                ).orElseThrow(() ->
                        new ExpenseNotFoundException(
                                "Expense not found"
                        )
                );

        return mapToResponse(expense);
    }

    // Update expense
    public ExpenseResponse updateExpense(
            Long expenseId,
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

        // Find expense belonging to user
        Expense expense =
                expenseRepository.findByIdAndUser(
                        expenseId,
                        user
                ).orElseThrow(() ->
                        new ExpenseNotFoundException(
                                "Expense not found"
                        )
                );

        // Update fields
        expense.setTitle(request.getTitle());
        expense.setAmount(request.getAmount());
        expense.setCategory(request.getCategory());
        expense.setDescription(request.getDescription());
        expense.setExpenseDate(request.getExpenseDate());

        // Save updated expense
        Expense updatedExpense =
                expenseRepository.save(expense);

        return mapToResponse(updatedExpense);
    }

    // Delete expense
    public String deleteExpense(
            Long expenseId,
            String email
    ) {

        // Get logged-in user
        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                "User not found"
                        )
                );

        // Find expense belonging to user
        Expense expense =
                expenseRepository.findByIdAndUser(
                        expenseId,
                        user
                ).orElseThrow(() ->
                        new ExpenseNotFoundException(
                                "Expense not found"
                        )
                );

        // Delete expense
        expenseRepository.delete(expense);

        return "Expense deleted successfully";
    }

    // Filter expenses by category
    public List<ExpenseResponse> getExpensesByCategory(
            Category category,
            String email
    ) {

        // Get logged-in user
        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                "User not found"
                        )
                );

        // Get filtered expenses
        List<Expense> expenses =
                expenseRepository.findByUserAndCategory(
                        user,
                        category
                );

        // Convert entity list -> DTO list
        return expenses.stream()
                .map(this::mapToResponse)
                .toList();
    }

    // Filter expenses by date
    public List<ExpenseResponse> getExpensesByDate(
            LocalDate date,
            String email
    ) {

        // Get logged-in user
        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                "User not found"
                        )
                );

        // Get filtered expenses
        List<Expense> expenses =
                expenseRepository.findByUserAndExpenseDate(
                        user,
                        date
                );

        // Convert entity list -> DTO list
        return expenses.stream()
                .map(this::mapToResponse)
                .toList();
    }

    // Search expenses by title
    public List<ExpenseResponse> searchExpenses(
            String keyword,
            String email
    ) {

        // Get logged-in user
        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                "User not found"
                        )
                );

        // Search expenses
        List<Expense> expenses =
                expenseRepository
                        .findByUserAndTitleContainingIgnoreCase(
                                user,
                                keyword
                        );

        // Convert entity list -> DTO list
        return expenses.stream()
                .map(this::mapToResponse)
                .toList();
    }

    // Get paginated expenses
    public Page<ExpenseResponse> getPaginatedExpenses(
            int page,
            int size,
            String email
    ) {

        // Get logged-in user
        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                "User not found"
                        )
                );

        // Create pageable object
        Pageable pageable =
                PageRequest.of(page, size);

        // Fetch paginated expenses
        Page<Expense> expensePage =
                expenseRepository.findByUser(
                        user,
                        pageable
                );

        // Convert entity page -> DTO page
        return expensePage.map(this::mapToResponse);
    }

    // Sort expenses
    public List<ExpenseResponse> sortExpenses(
            String field,
            String email
    ) {

        // Get logged-in user
        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                "User not found"
                        )
                );

        // Create sorting object
        Sort sort = Sort.by(
                Sort.Direction.ASC,
                field
        );

        // Fetch sorted expenses
        List<Expense> expenses =
                expenseRepository.findByUser(
                        user,
                        sort
                );

        // Convert entity list -> DTO list
        return expenses.stream()
                .map(this::mapToResponse)
                .toList();
    }
}