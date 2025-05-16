package com.robotbot.finance_tracker_server.controllers;

import com.robotbot.finance_tracker_server.domain.dto.analytics.AnalyticsDailySummaryResponse;
import com.robotbot.finance_tracker_server.security.UserPrincipal;
import com.robotbot.finance_tracker_server.services.AnalyticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/analytics")
@RequiredArgsConstructor
public class AnalyticsController {

    private final AnalyticsService analyticsService;

    @GetMapping("/daily-summary")
    public ResponseEntity<AnalyticsDailySummaryResponse> getDailySummary(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        AnalyticsDailySummaryResponse response = analyticsService.getDailySummary(userPrincipal, startDate, endDate);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/categories")
    public ResponseEntity getCategoriesAnalytics(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @RequestParam Boolean isExpense,
            @RequestParam(value = "sortOrder", defaultValue = "asc") String sortOrder
    ) {
        return ResponseEntity.ok(analyticsService.getCategoriesAnalytics(userPrincipal, isExpense, sortOrder));
    }
}
