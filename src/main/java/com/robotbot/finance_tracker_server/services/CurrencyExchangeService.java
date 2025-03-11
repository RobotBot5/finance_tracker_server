package com.robotbot.finance_tracker_server.services;

import com.robotbot.finance_tracker_server.domain.dto.ExchangeRateResponse;

public interface CurrencyExchangeService {

    ExchangeRateResponse getExchangeRates();
}
