package com.robotbot.finance_tracker_server.domain.dto.analytics;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class AnalyticsDailySummaryResponse {

    private List<AnalyticsDailySummary> analytics;

}
