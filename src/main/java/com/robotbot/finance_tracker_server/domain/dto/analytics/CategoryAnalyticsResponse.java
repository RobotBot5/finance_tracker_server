package com.robotbot.finance_tracker_server.domain.dto.analytics;

import com.robotbot.finance_tracker_server.domain.dto.category.CategoryResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.math.BigDecimal;

@Data
@Getter
@Builder
@AllArgsConstructor
public class CategoryAnalyticsResponse {

    private CategoryResponse category;

    private BigDecimal totalAmount;
}
