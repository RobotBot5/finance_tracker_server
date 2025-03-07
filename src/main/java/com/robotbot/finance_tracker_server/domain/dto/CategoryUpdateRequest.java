package com.robotbot.finance_tracker_server.domain.dto;

import lombok.Getter;

@Getter
public class CategoryUpdateRequest {

    private String name;

    private Long iconId;
}
