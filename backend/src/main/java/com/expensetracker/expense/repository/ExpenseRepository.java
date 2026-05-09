package com.expensetracker.expense.repository;

import com.expensetracker.expense.entity.Category;
import com.expensetracker.expense.entity.Expense;
import com.expensetracker.user.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public interface ExpenseRepository
        extends JpaRepository<Expense, Long> {

    // Get all expenses of a user
    List<Expense> findByUser(User user);

    // Get expense by id and user
    Optional<Expense> findByIdAndUser(
            Long id,
            User user
    );

    // Filter by category
    List<Expense> findByUserAndCategory(
            User user,
            Category category
    );

    // Filter by date
    List<Expense> findByUserAndExpenseDate(
            User user,
            LocalDate expenseDate
    );

    // Search by title
    List<Expense> findByUserAndTitleContainingIgnoreCase(
            User user,
            String keyword
    );

    // Pagination
    Page<Expense> findByUser(
            User user,
            Pageable pageable
    );

    // Sorting
    List<Expense> findByUser(
            User user,
            Sort sort
    );
}