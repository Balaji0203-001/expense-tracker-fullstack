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

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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

    // Total expenses
    @Query("""
        SELECT COALESCE(SUM(e.amount), 0)
        FROM Expense e
        WHERE e.user = :user
        """)
    Double getTotalExpenses(
            @Param("user") User user
    );

    // Highest expense
    @Query("""
        SELECT COALESCE(MAX(e.amount), 0)
        FROM Expense e
        WHERE e.user = :user
        """)
    Double getHighestExpense(
            @Param("user") User user
    );

    // Total transactions
    @Query("""
        SELECT COUNT(e)
        FROM Expense e
        WHERE e.user = :user
        """)
    Long getTotalTransactions(
            @Param("user") User user
    );

    // Category totals
    @Query("""
        SELECT e.category, SUM(e.amount)
        FROM Expense e
        WHERE e.user = :user
        GROUP BY e.category
        """)
    List<Object[]> getCategoryTotals(
            @Param("user") User user
    );

    // Monthly totals
    @Query("""
        SELECT MONTH(e.expenseDate), SUM(e.amount)
        FROM Expense e
        WHERE e.user = :user
        GROUP BY MONTH(e.expenseDate)
        ORDER BY MONTH(e.expenseDate)
        """)
    List<Object[]> getMonthlyTotals(
            @Param("user") User user
    );
}