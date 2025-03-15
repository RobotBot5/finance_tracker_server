package com.robotbot.finance_tracker_server.domain.dto;

import com.robotbot.finance_tracker_server.domain.entities.CurrencyEntity;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class CurrenciesResponse {

    private List<CurrencyResponse> currencies;

}
