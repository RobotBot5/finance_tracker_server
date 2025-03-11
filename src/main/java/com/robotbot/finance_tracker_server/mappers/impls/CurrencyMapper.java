package com.robotbot.finance_tracker_server.mappers.impls;

import com.robotbot.finance_tracker_server.domain.dto.CurrencyResponse;
import com.robotbot.finance_tracker_server.domain.entities.CurrencyEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CurrencyMapper {

    public CurrencyResponse mapEntityToResponse(List<CurrencyEntity> currencies) {
        return CurrencyResponse.builder().currencies(currencies).build();
    }
}
