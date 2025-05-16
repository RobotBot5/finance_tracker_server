package com.robotbot.finance_tracker_server.domain.dto.analytics;

import com.robotbot.finance_tracker_server.domain.entities.CurrencyEntity;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Builder
public class CategoriesAnalyticsResponse {

    private List<CategoryAnalyticsResponse> result;

    private BigDecimal totalAmount;

    private CurrencyEntity targetCurrency;
}
