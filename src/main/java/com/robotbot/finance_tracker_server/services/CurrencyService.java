package com.robotbot.finance_tracker_server.services;

import com.robotbot.finance_tracker_server.domain.dto.currency.CurrenciesResponse;
import com.robotbot.finance_tracker_server.domain.dto.currency.CurrencyResponse;

public interface CurrencyService {

    CurrenciesResponse getCurrencies();

    CurrencyResponse getCurrencyByCode(String code);
}
