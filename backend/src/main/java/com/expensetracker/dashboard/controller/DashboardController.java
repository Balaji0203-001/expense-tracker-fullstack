package com.expensetracker.dashboard.controller;

import com.expensetracker.dashboard.dto.CategoryTotalResponse;
import com.expensetracker.dashboard.dto.DashboardSummaryResponse;
import com.expensetracker.dashboard.dto.MonthlyExpenseResponse;
import com.expensetracker.dashboard.service.DashboardService;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    // Dashboard summary
    @GetMapping("/summary")
    public DashboardSummaryResponse getDashboardSummary(
            Authentication authentication
    ) {

        // Get logged-in user email
        String email = authentication.getName();

        return dashboardService.getDashboardSummary(
                email
        );
    }

    // Category totals
    @GetMapping("/category-total")
    public List<CategoryTotalResponse> getCategoryTotals(
            Authentication authentication
    ) {

        // Get logged-in user email
        String email = authentication.getName();

        return dashboardService.getCategoryTotals(
                email
        );
    }

    // Monthly totals
    @GetMapping("/monthly")
    public List<MonthlyExpenseResponse> getMonthlyTotals(
            Authentication authentication
    ) {

        // Get logged-in user email
        String email = authentication.getName();

        return dashboardService.getMonthlyTotals(
                email
        );
    }
}