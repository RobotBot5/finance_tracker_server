package com.robotbot.finance_tracker_server.controllers;

import com.robotbot.finance_tracker_server.domain.dto.ExchangeRateResponse;
import com.robotbot.finance_tracker_server.services.CurrencyExchangeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestCurrencyController {

    private final CurrencyExchangeService currencyExchangeService;

    @GetMapping
    public ResponseEntity<ExchangeRateResponse> getExchangeRates() {
        return ResponseEntity.ok(currencyExchangeService.getExchangeRates());
    }
}
