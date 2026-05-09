package com.expensetracker.expense.repository;

import com.expensetracker.expense.entity.Expense;
import com.expensetracker.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ExpenseRepository
        extends JpaRepository<Expense, Long> {

    // Get all expenses of a user
    List<Expense> findByUser(User user);

    // Get expense by id and user
    Optional<Expense> findByIdAndUser(
            Long id,
            User user
    );
}