package com.robotbot.finance_tracker_server.domain.dto.analytics;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class AnalyticsDailySummary {

    private LocalDate date;

    private BigDecimal totalIncome;

    private BigDecimal totalExpense;

    public BigDecimal net;

}
