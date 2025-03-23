package com.robotbot.finance_tracker_server.services;

import com.robotbot.finance_tracker_server.domain.dto.analytics.AnalyticsDailySummaryResponse;
import com.robotbot.finance_tracker_server.security.UserPrincipal;

import java.time.LocalDate;
import java.time.ZoneId;

public interface AnalyticsService {

    AnalyticsDailySummaryResponse getDailySummary(
            UserPrincipal userPrincipal,
            LocalDate startDate,
            LocalDate endDate,
            ZoneId zoneId
    );

}
