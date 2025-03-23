package com.robotbot.finance_tracker_server.domain.dto.account;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class AccountUpdateRequest {

    private String name;

    private String currencyCode;

    private BigDecimal balance;

    private Long iconId;
}
