package com.robotbot.finance_tracker_server.domain.dto.category;

import lombok.Getter;

@Getter
public class CategoryUpdateRequest {

    private String name;

    private Long iconId;
}
