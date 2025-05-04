package com.robotbot.finance_tracker_server.mappers.impls;

import com.robotbot.finance_tracker_server.domain.dto.analytics.CategoriesAnalyticsResponse;
import com.robotbot.finance_tracker_server.domain.dto.analytics.CategoryAnalyticsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AnalyticsMapper {

    private final CategoryMapper categoryMapper;

    public CategoriesAnalyticsResponse mapCategoryAnalyticsListToResponse(List<CategoryAnalyticsResponse> categoryAnalyticsList) {
        return CategoriesAnalyticsResponse.builder()
                .result(categoryAnalyticsList)
                .build();
    }
}
