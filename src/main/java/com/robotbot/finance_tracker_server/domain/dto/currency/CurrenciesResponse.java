package com.robotbot.finance_tracker_server.domain.dto.currency;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class CurrenciesResponse {

    private List<CurrencyResponse> currencies;

}
