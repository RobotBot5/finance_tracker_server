package com.robotbot.finance_tracker_server.mappers.impls;

import com.robotbot.finance_tracker_server.domain.dto.analytics.CategoriesAnalyticsResponse;
import com.robotbot.finance_tracker_server.domain.dto.analytics.CategoryAnalyticsResponse;
import com.robotbot.finance_tracker_server.domain.entities.CurrencyEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
@RequiredArgsConstructor
public class AnalyticsMapper {

    private final CategoryMapper categoryMapper;

    public CategoriesAnalyticsResponse mapCategoryAnalyticsListToResponse(
            List<CategoryAnalyticsResponse> categoryAnalyticsList,
            BigDecimal totalAmount,
            CurrencyEntity targetCurrency
    ) {
        return CategoriesAnalyticsResponse.builder()
                .result(categoryAnalyticsList)
                .totalAmount(totalAmount)
                .targetCurrency(targetCurrency)
                .build();
    }
}
