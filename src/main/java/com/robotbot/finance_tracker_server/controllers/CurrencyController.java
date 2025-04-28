package com.robotbot.finance_tracker_server.controllers;

import com.robotbot.finance_tracker_server.domain.dto.ExchangeRateResponse;
import com.robotbot.finance_tracker_server.domain.dto.currency.CurrenciesResponse;
import com.robotbot.finance_tracker_server.services.CurrencyExchangeService;
import com.robotbot.finance_tracker_server.services.CurrencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/currencies")
@RequiredArgsConstructor
public class CurrencyController {

    private final CurrencyService currencyService;
    private final CurrencyExchangeService currencyExchangeService;

    @GetMapping
    public ResponseEntity<CurrenciesResponse> getCurrencies() {
        return ResponseEntity.ok().body(currencyService.getCurrencies());
    }

    @GetMapping("/{code}")
    public ResponseEntity getCurrencyByCode(@PathVariable String code) {
        return ResponseEntity.ok().body(currencyService.getCurrencyByCode(code));
    }

    @GetMapping("/rates")
    public ResponseEntity<ExchangeRateResponse> getCurrenciesRates() {
        return ResponseEntity.ok().body(currencyExchangeService.getExchangeRates());
    }

}
