package com.robotbot.finance_tracker_server.mappers.impls;

import com.robotbot.finance_tracker_server.domain.dto.account.AccountCreateRequest;
import com.robotbot.finance_tracker_server.domain.dto.account.AccountResponse;
import com.robotbot.finance_tracker_server.domain.dto.account.AccountsResponse;
import com.robotbot.finance_tracker_server.domain.entities.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
@RequiredArgsConstructor
public class AccountMapper {

    private final ModelMapper mapper;
    private final CurrencyMapper currencyMapper;

    public AccountEntity mapRequestToEntity(
            AccountCreateRequest accountCreateRequest,
            UserEntity userEntity,
            IconEntity iconEntity,
            CurrencyEntity currencyEntity
    ) {
        return AccountEntity.builder()
                .name(accountCreateRequest.getName())
                .user(userEntity)
                .balance(accountCreateRequest.getBalance() == null ? BigDecimal.ZERO : accountCreateRequest.getBalance())
                .icon(iconEntity)
                .currency(currencyEntity)
                .build();
    }

    public AccountResponse mapEntityToResponse(AccountEntity accountEntity) {
        return AccountResponse.builder()
                .id(accountEntity.getId())
                .name(accountEntity.getName())
                .balance(accountEntity.getBalance())
                .currency(currencyMapper.mapEntityToResponse(accountEntity.getCurrency()))
                .icon(accountEntity.getIcon())
                .build();
    }

    public AccountsResponse mapEntitiesListToResponse(List<AccountEntity> accounts) {
        return AccountsResponse.builder()
                .accounts(
                        accounts
                                .stream()
                                .map(this::mapEntityToResponse)
                                .toList()
                ).build();
    }
}
