package com.expensetracker.dashboard.service;

import com.expensetracker.dashboard.dto.CategoryTotalResponse;
import com.expensetracker.dashboard.dto.DashboardSummaryResponse;
import com.expensetracker.dashboard.dto.MonthlyExpenseResponse;
import com.expensetracker.expense.repository.ExpenseRepository;
import com.expensetracker.user.entity.User;
import com.expensetracker.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final ExpenseRepository expenseRepository;

    private final UserRepository userRepository;

    // Dashboard summary
    public DashboardSummaryResponse getDashboardSummary(
            String email
    ) {

        // Get logged-in user
        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                "User not found"
                        )
                );

        // Fetch analytics
        Double totalExpenses =
                expenseRepository.getTotalExpenses(user);

        Double highestExpense =
                expenseRepository.getHighestExpense(user);

        Long totalTransactions =
                expenseRepository.getTotalTransactions(user);

        return new DashboardSummaryResponse(
                totalExpenses,
                highestExpense,
                totalTransactions
        );
    }

    // Category totals
    public List<CategoryTotalResponse> getCategoryTotals(
            String email
    ) {

        // Get logged-in user
        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                "User not found"
                        )
                );

        // Fetch category totals
        List<Object[]> results =
                expenseRepository.getCategoryTotals(user);

        // Convert results -> DTOs
        return results.stream()
                .map(result -> new CategoryTotalResponse(
                        result[0].toString(),
                        (Double) result[1]
                ))
                .toList();
    }

    // Monthly totals
    public List<MonthlyExpenseResponse> getMonthlyTotals(
            String email
    ) {

        // Get logged-in user
        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                "User not found"
                        )
                );

        // Fetch monthly totals
        List<Object[]> results =
                expenseRepository.getMonthlyTotals(user);

        // Convert results -> DTOs
        return results.stream()
                .map(result -> new MonthlyExpenseResponse(
                        (Integer) result[0],
                        (Double) result[1]
                ))
                .toList();
    }
}