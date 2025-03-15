package com.robotbot.finance_tracker_server.services;

import com.robotbot.finance_tracker_server.domain.dto.CurrenciesResponse;

public interface CurrencyService {

    CurrenciesResponse getCurrencies();
}
