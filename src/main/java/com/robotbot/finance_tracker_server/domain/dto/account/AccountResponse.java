package com.robotbot.finance_tracker_server.domain.dto.account;

import com.robotbot.finance_tracker_server.domain.dto.currency.CurrencyResponse;
import com.robotbot.finance_tracker_server.domain.entities.IconEntity;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.math.BigDecimal;

@Data
@Getter
@Builder
public class AccountResponse {

    private Long id;

    private String name;

    private CurrencyResponse currency;

    private BigDecimal balance;

    private IconEntity icon;
}
