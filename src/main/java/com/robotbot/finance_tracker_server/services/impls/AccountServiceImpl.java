package com.robotbot.finance_tracker_server.services.impls;

import com.robotbot.finance_tracker_server.domain.dto.account.*;
import com.robotbot.finance_tracker_server.domain.entities.AccountEntity;
import com.robotbot.finance_tracker_server.domain.entities.CurrencyEntity;
import com.robotbot.finance_tracker_server.domain.entities.IconEntity;
import com.robotbot.finance_tracker_server.domain.entities.UserEntity;
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
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Log4j2
@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final UserService userService;
    private final IconRepository iconRepository;
    private final AccountMapper mapper;
    private final CurrencyRepository currencyRepository;
    private final CurrencyRateUpdater currencyRateUpdater;

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
    public AccountResponse getAccountById(UserPrincipal userPrincipal, Long accountId) {
        UserEntity userEntity = userService.getUserByPrincipal(userPrincipal);
        AccountEntity accountEntity = accountRepository
                .findById(accountId)
                .orElseThrow(() -> new EntityWithIdDoesntExistsException("Account not found"));
        if (!Objects.equals(accountEntity.getUser().getId(), userEntity.getId())) {
            throw new AuthenticationException("You can't get this account");
        }
        return mapper.mapEntityToResponse(accountEntity);
    }

    @Override
    public void updateAccount(Long accountId, AccountUpdateRequest accountUpdateRequest, UserPrincipal userPrincipal) {
        AccountEntity account = getAccountEntity(accountId, userPrincipal);
        if (accountUpdateRequest.getName() != null) {
            account.setName(accountUpdateRequest.getName());
        }

        if (accountUpdateRequest.getBalance() != null) {
            account.setBalance(accountUpdateRequest.getBalance());
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

    @Override
    public TotalBalanceResponse getTotalBalance(UserPrincipal userPrincipal) {
        UserEntity userEntity = userService.getUserByPrincipal(userPrincipal);
        CurrencyEntity targetCurrency = userEntity.getTargetCurrency();
        List<AccountEntity> accounts = accountRepository.findByUser(userEntity);

        BigDecimal totalBalance = BigDecimal.ZERO;
        for (AccountEntity account : accounts) {
            BigDecimal accountBalance = account.getBalance();
            CurrencyEntity accountCurrency = account.getCurrency();

            if (targetCurrency.equals(accountCurrency)) {
                totalBalance = totalBalance.add(accountBalance);
            } else {
                log.info("account balance: " + accountBalance.toPlainString());
                log.info("account currency: " + accountCurrency.getCode());
                log.info("target currency: " + targetCurrency.getCode());
                BigDecimal amountInTargetCurrency = currencyRateUpdater.convert(accountBalance, accountCurrency, targetCurrency);
                log.info("converted amount: " + amountInTargetCurrency.toPlainString());
                totalBalance = totalBalance.add(amountInTargetCurrency);
            }
        }
        return mapper.mapTotalBalanceToResponse(totalBalance, targetCurrency);
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
