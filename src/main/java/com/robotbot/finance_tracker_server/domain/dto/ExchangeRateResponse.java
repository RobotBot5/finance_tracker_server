package com.robotbot.finance_tracker_server.domain.dto;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import lombok.Data;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Data
public class ExchangeRateResponse {

    // Этот Map будет содержать все динамические пары "валюта-курс"
    private Map<String, BigDecimal> rates = new HashMap<>();

    @JsonAnySetter
    public void addRate(String key, BigDecimal value) {
        rates.put(key, value);
    }
}

