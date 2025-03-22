package com.robotbot.finance_tracker_server.domain.dto.category;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class CategoriesResponse {

    private List<CategoryResponse> categories;
}
