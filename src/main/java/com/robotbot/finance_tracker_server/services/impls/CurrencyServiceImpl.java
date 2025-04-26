package com.robotbot.finance_tracker_server.services.impls;

import com.robotbot.finance_tracker_server.domain.dto.currency.CurrenciesResponse;
import com.robotbot.finance_tracker_server.domain.dto.currency.CurrencyResponse;
import com.robotbot.finance_tracker_server.mappers.impls.CurrencyMapper;
import com.robotbot.finance_tracker_server.repositories.CurrencyRepository;
import com.robotbot.finance_tracker_server.services.CurrencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class CurrencyServiceImpl implements CurrencyService {

    private final CurrencyRepository currencyRepository;
    private final CurrencyMapper currencyMapper;

    @Override
    public CurrenciesResponse getCurrencies() {
        return currencyMapper.mapEntitiesToResponse(StreamSupport.stream(
                currencyRepository.findAll().spliterator(),
                false
        ).collect(Collectors.toList()));
    }

    @Override
    public CurrencyResponse getCurrencyByCode(String code) {
        return currencyMapper.mapEntityToResponse(currencyRepository.findByCode(code));
    }
}
