package com.robotbot.finance_tracker_server.services.impls;

import com.robotbot.finance_tracker_server.domain.dto.ExchangeRateResponse;
import com.robotbot.finance_tracker_server.domain.entities.CurrencyEntity;
import com.robotbot.finance_tracker_server.domain.exceptions.ExchangeApiException;
import com.robotbot.finance_tracker_server.repositories.CurrencyRepository;
import com.robotbot.finance_tracker_server.services.CurrencyExchangeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class CurrencyExchangeServiceImpl implements CurrencyExchangeService {

    private final RestTemplate restTemplate;
    private final CurrencyRepository currencyRepository;

    @Override
    public ExchangeRateResponse getExchangeRates() {
        try {
            String exchangeApiUrl = "https://min-api.cryptocompare.com/data/price";
            String targetCurrencyParam = "fsym";
            String notTargetCurrenciesParam = "tsyms";

            URI uri = UriComponentsBuilder.fromUriString(exchangeApiUrl)
                    .queryParam(targetCurrencyParam, currencyRepository.findByTargetTrue().getCode())
                    .queryParam(notTargetCurrenciesParam, currencyRepository.findByTargetFalse()
                            .stream().map(CurrencyEntity::getCode)
                            .collect(Collectors.joining(",")))
                    .build().toUri();
            ResponseEntity<ExchangeRateResponse> response =
                    restTemplate.getForEntity(uri, ExchangeRateResponse.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                return response.getBody();
            } else {
                throw new ExchangeApiException("Error retrieving exchange rates, status: " + response.getStatusCode());
            }
        } catch (RestClientException ex) {
            throw new ExchangeApiException("Failed to get exchange rates", ex);
        }
    }
}

