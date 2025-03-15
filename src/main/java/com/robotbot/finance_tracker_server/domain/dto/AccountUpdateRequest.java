package com.robotbot.finance_tracker_server.domain.dto;

import lombok.Getter;

@Getter
public class AccountUpdateRequest {

    private String name;

    private String currencyCode;

    private Long iconId;
}
