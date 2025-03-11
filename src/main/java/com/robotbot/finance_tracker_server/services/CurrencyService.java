package com.robotbot.finance_tracker_server.services;

import com.robotbot.finance_tracker_server.domain.dto.CurrencyResponse;

public interface CurrencyService {

    CurrencyResponse getCurrencies();
}
