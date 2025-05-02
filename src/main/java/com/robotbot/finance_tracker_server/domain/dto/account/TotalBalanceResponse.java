package com.robotbot.finance_tracker_server.domain.dto.account;

import com.robotbot.finance_tracker_server.domain.dto.currency.CurrencyResponse;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Builder
@Getter
public class TotalBalanceResponse {

    BigDecimal totalBalance;

    CurrencyResponse targetCurrency;
}
