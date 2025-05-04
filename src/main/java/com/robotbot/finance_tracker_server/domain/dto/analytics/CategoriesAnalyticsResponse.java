package com.robotbot.finance_tracker_server.domain.dto.analytics;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class CategoriesAnalyticsResponse {

    private List<CategoryAnalyticsResponse> result;
}
