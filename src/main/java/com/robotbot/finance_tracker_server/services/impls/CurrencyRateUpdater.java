package com.robotbot.finance_tracker_server.services.impls;

import com.robotbot.finance_tracker_server.domain.dto.ExchangeRateResponse;
import com.robotbot.finance_tracker_server.domain.entities.CurrencyEntity;
import com.robotbot.finance_tracker_server.domain.entities.CurrencyRatesEntity;
import com.robotbot.finance_tracker_server.domain.exceptions.EntityWithIdDoesntExistsException;
import com.robotbot.finance_tracker_server.domain.exceptions.ExchangeApiException;
import com.robotbot.finance_tracker_server.repositories.CurrencyRatesRepository;
import com.robotbot.finance_tracker_server.repositories.CurrencyRepository;
import com.robotbot.finance_tracker_server.services.CurrencyExchangeService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Log4j2
@Transactional
public class CurrencyRateUpdater {

    private final CurrencyExchangeService currencyExchangeService;
    private final CurrencyRatesRepository currencyRatesRepository;
    private final CurrencyRepository currencyRepository;

    @Scheduled(fixedRateString = "${currency.rates.update-interval}")
    public void updateCurrencyRates() {
        log.info("Starting currency rates update...");
        try {
            ExchangeRateResponse response = currencyExchangeService.getExchangeRates();
            if (response != null) {
                Map<String, BigDecimal> rates = response.getRates();
                for (Map.Entry<String, BigDecimal> entry : rates.entrySet()) {
                    String currencyCode = entry.getKey();
                    String rate = entry.getValue().toPlainString();

                    CurrencyEntity currencyEntity = currencyRepository.findByCode(currencyCode);
                    if (currencyEntity != null) {
                        CurrencyRatesEntity currencyRateEntity = currencyRatesRepository.findByCurrency(currencyEntity)
                                .orElse(CurrencyRatesEntity.builder().currency(currencyEntity).build());
                        currencyRateEntity.setRate(rate);
                        currencyRateEntity.setLastUpdated(Instant.now());

                        log.info(currencyRateEntity.toString());
                        currencyRatesRepository.save(currencyRateEntity);
                    }
                }
                log.info("Currency rates update completed successfully.");
            }
        } catch (ExchangeApiException ex) {
            log.error("Error updating currency rates: {}", ex.getMessage(), ex);
        }
    }

    public BigDecimal convert(BigDecimal amount, CurrencyEntity currencyFrom, CurrencyEntity currencyTo) {
        BigDecimal rateFrom;
        if (currencyFrom.getTarget()) {
            rateFrom = BigDecimal.ONE;
        } else {
            var currencyRateFrom = currencyRatesRepository
                    .findByCurrency(currencyFrom)
                    .orElseThrow(() -> new EntityWithIdDoesntExistsException("Currency rate not found"));
            rateFrom = new BigDecimal(currencyRateFrom.getRate());
        }

        BigDecimal rateTo;
        if (currencyTo.getTarget()) {
            rateTo = BigDecimal.ONE;
        } else {
            CurrencyRatesEntity currencyRateTo = currencyRatesRepository
                    .findByCurrency(currencyTo)
                    .orElseThrow(() -> new EntityWithIdDoesntExistsException("Currency rate not found"));
            rateTo = new BigDecimal(currencyRateTo.getRate());
        }

        return amount.multiply(rateTo.divide(rateFrom, 2, RoundingMode.HALF_UP));
    }
}

