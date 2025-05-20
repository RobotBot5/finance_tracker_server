package com.robotbot.finance_tracker_server.domain.dto.category;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CategoryUpdateRequest {

    private String name;

    private Long iconId;
}
