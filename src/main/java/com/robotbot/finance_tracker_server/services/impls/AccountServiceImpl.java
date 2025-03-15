package com.robotbot.finance_tracker_server.services.impls;

import com.robotbot.finance_tracker_server.domain.dto.AccountCreateRequest;
import com.robotbot.finance_tracker_server.domain.dto.AccountUpdateRequest;
import com.robotbot.finance_tracker_server.domain.dto.AccountsResponse;
import com.robotbot.finance_tracker_server.domain.dto.CategoryCreateRequest;
import com.robotbot.finance_tracker_server.domain.entities.*;
import com.robotbot.finance_tracker_server.domain.exceptions.AuthenticationException;
import com.robotbot.finance_tracker_server.domain.exceptions.EntityWithIdDoesntExistsException;
import com.robotbot.finance_tracker_server.mappers.impls.AccountMapper;
import com.robotbot.finance_tracker_server.repositories.AccountRepository;
import com.robotbot.finance_tracker_server.repositories.CurrencyRepository;
import com.robotbot.finance_tracker_server.repositories.IconRepository;
import com.robotbot.finance_tracker_server.security.UserPrincipal;
import com.robotbot.finance_tracker_server.services.AccountService;
import com.robotbot.finance_tracker_server.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final UserService userService;
    private final IconRepository iconRepository;
    private final AccountMapper mapper;
    private final CurrencyRepository currencyRepository;

    @Override
    public void addAccount(AccountCreateRequest accountCreateRequest, UserPrincipal userPrincipal) {
        UserEntity userEntity = userService.getUserByPrincipal(userPrincipal);
        IconEntity iconEntity = iconRepository
                .findById(accountCreateRequest.getIconId())
                .orElseThrow(() -> new EntityWithIdDoesntExistsException("Icon not found"));
        CurrencyEntity currencyEntity = currencyRepository
                .findById(accountCreateRequest.getCurrencyCode())
                .orElseThrow(() -> new EntityWithIdDoesntExistsException("Currency not found"));
        AccountEntity accountEntity = mapper.mapRequestToEntity(
                accountCreateRequest,
                userEntity,
                iconEntity,
                currencyEntity
        );
        accountRepository.save(accountEntity);
    }

    @Override
    public AccountsResponse getAccountsByUser(UserPrincipal userPrincipal) {
        UserEntity userEntity = userService.getUserByPrincipal(userPrincipal);
        List<AccountEntity> accounts = accountRepository.findByUser(userEntity);
        return mapper.mapEntitiesListToResponse(accounts);
    }

    @Override
    public void updateAccount(Long accountId, AccountUpdateRequest accountUpdateRequest, UserPrincipal userPrincipal) {
        AccountEntity account = getAccountEntity(accountId, userPrincipal);
        if (accountUpdateRequest.getName() != null) {
            account.setName(accountUpdateRequest.getName());
        }

        if (accountUpdateRequest.getCurrencyCode() != null) {
            CurrencyEntity currency = currencyRepository.findByCode(accountUpdateRequest.getCurrencyCode());
            if (currency == null) {
                throw new EntityWithIdDoesntExistsException("Currency not found");
            }
            account.setCurrency(currency);
        }

        if (accountUpdateRequest.getIconId() != null) {
            IconEntity icon = iconRepository.findById(accountUpdateRequest.getIconId())
                    .orElseThrow(() -> new EntityWithIdDoesntExistsException("Icon not found"));
            account.setIcon(icon);
        }

        accountRepository.save(account);
    }

    @Override
    public void deleteAccount(Long id, UserPrincipal userPrincipal) {
        AccountEntity account = getAccountEntity(id, userPrincipal);
        accountRepository.delete(account);
    }

    private AccountEntity getAccountEntity(Long id, UserPrincipal userPrincipal) {
        UserEntity userEntity = userService.getUserByPrincipal(userPrincipal);
        AccountEntity account = accountRepository
                .findById(id)
                .orElseThrow(() -> new EntityWithIdDoesntExistsException("Account not found"));
        if (account.getUser() == null || !account.getUser().getId().equals(userEntity.getId())) {
            throw new AuthenticationException();
        }
        return account;
    }
}
