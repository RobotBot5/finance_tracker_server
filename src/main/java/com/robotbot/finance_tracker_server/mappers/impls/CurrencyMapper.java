package com.robotbot.finance_tracker_server.mappers.impls;

import com.robotbot.finance_tracker_server.domain.dto.currency.CurrenciesResponse;
import com.robotbot.finance_tracker_server.domain.dto.currency.CurrencyResponse;
import com.robotbot.finance_tracker_server.domain.entities.CurrencyEntity;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CurrencyMapper {

    private final ModelMapper mapper;

    public CurrenciesResponse mapEntitiesToResponse(List<CurrencyEntity> currencies) {
        return CurrenciesResponse.builder()
                .currencies(
                        currencies.stream()
                                .map(this::mapEntityToResponse)
                                .toList())
                .build();
    }

    public CurrencyResponse mapEntityToResponse(CurrencyEntity currency) {
        return mapper.map(currency, CurrencyResponse.class);
    }
}
