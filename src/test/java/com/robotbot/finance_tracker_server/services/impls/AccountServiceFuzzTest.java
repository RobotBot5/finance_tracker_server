package com.robotbot.finance_tracker_server.services.impls;

import com.code_intelligence.jazzer.api.FuzzedDataProvider;
import com.code_intelligence.jazzer.junit.FuzzTest;
import com.robotbot.finance_tracker_server.domain.dto.account.*;
import com.robotbot.finance_tracker_server.domain.dto.currency.CurrencyResponse;
import com.robotbot.finance_tracker_server.domain.entities.*;
import com.robotbot.finance_tracker_server.domain.exceptions.AuthenticationException;
import com.robotbot.finance_tracker_server.domain.exceptions.EntityWithIdDoesntExistsException;
import com.robotbot.finance_tracker_server.mappers.impls.AccountMapper;
import com.robotbot.finance_tracker_server.repositories.*;
import com.robotbot.finance_tracker_server.security.UserPrincipal;
import com.robotbot.finance_tracker_server.services.UserService;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class AccountServiceFuzzTest {
    private final AccountRepository accountRepository = mock(AccountRepository.class);
    private final UserService userService = mock(UserService.class);
    private final IconRepository iconRepository = mock(IconRepository.class);
    private final AccountMapper mapper = mock(AccountMapper.class);
    private final CurrencyRepository currencyRepository = mock(CurrencyRepository.class);
    private final CurrencyRateUpdater currencyRateUpdater = mock(CurrencyRateUpdater.class);

    private final UserEntity fixedUser = UserEntity.builder().id(1L).build();
    private final UserPrincipal fixedPrincipal = UserPrincipal.builder()
            .userId(1L)
            .email("fuzz@example.com")
            .password("pwd")
            .build();

    private final IconEntity defaultIcon = IconEntity.builder()
            .id(1L)
            .name("fuzzIcon")
            .path("/icons/fuzz.png")
            .build();
    private final CurrencyEntity defaultCurrency = CurrencyEntity.builder()
            .code("USD")
            .symbol("$")
            .target(true)
            .build();

    private final AccountServiceImpl service;

    public AccountServiceFuzzTest() {
        when(userService.getUserByPrincipal(any(UserPrincipal.class)))
                .thenReturn(fixedUser);

        when(iconRepository.findById(eq(1L))).thenReturn(Optional.of(defaultIcon));
        when(iconRepository.findById(longThat(id -> id != 1L)))
                .thenReturn(Optional.empty());

        when(currencyRepository.findById(eq("USD")))
                .thenReturn(Optional.of(defaultCurrency));
        when(currencyRepository.findById(argThat(code -> !"USD".equals(code))))
                .thenReturn(Optional.empty());
        when(currencyRepository.findByCode(eq("USD")))
                .thenReturn(defaultCurrency);
        when(currencyRepository.findByCode(argThat(code -> !"USD".equals(code))))
                .thenReturn(null);

        AccountEntity existing = new AccountEntity();
        existing.setId(1L);
        existing.setUser(fixedUser);
        existing.setName("initial");
        existing.setBalance(BigDecimal.valueOf(100));
        existing.setIcon(defaultIcon);
        existing.setCurrency(defaultCurrency);
        when(accountRepository.findById(eq(1L)))
                .thenReturn(Optional.of(existing));
        when(accountRepository.findById(longThat(id -> id != 1L)))
                .thenReturn(Optional.empty());
        when(accountRepository.findByUser(eq(fixedUser)))
                .thenReturn(Collections.emptyList());

        when(mapper.mapRequestToEntity(any(AccountCreateRequest.class),
                any(UserEntity.class),
                any(IconEntity.class),
                any(CurrencyEntity.class)))
                .thenAnswer(invocation -> {
                    AccountCreateRequest req = invocation.getArgument(0);
                    UserEntity user = invocation.getArgument(1);
                    IconEntity icon = invocation.getArgument(2);
                    CurrencyEntity cur = invocation.getArgument(3);
                    AccountEntity e = new AccountEntity();
                    e.setId(999L);
                    e.setName(req.getName());
                    e.setUser(user);
                    e.setIcon(icon);
                    e.setCurrency(cur);
                    e.setBalance(BigDecimal.ZERO);
                    return e;
                });
        when(mapper.mapEntitiesListToResponse(anyList()))
                .thenReturn(AccountsResponse.builder().accounts(List.of()).build());
        when(mapper.mapEntityToResponse(any(AccountEntity.class)))
                .thenAnswer(inv -> AccountResponse.builder()
                        .id(((AccountEntity) inv.getArgument(0)).getId())
                        .name("response")
                        .currency(new CurrencyResponse("USD", "$", "dollars"))
                        .balance(BigDecimal.ZERO)
                        .icon(defaultIcon)
                        .build());
        when(mapper.mapTotalBalanceToResponse(any(BigDecimal.class), any(CurrencyEntity.class)))
                .thenAnswer(inv -> {
                    BigDecimal total = inv.getArgument(0);
                    CurrencyEntity cur = inv.getArgument(1);
                    CurrencyResponse res = new CurrencyResponse(cur.getCode(), cur.getSymbol(), cur.getName());
                    return TotalBalanceResponse.builder()
                            .totalBalance(total)
                            .targetCurrency(res)
                            .build();
                });

        when(currencyRateUpdater.convert(any(BigDecimal.class), any(CurrencyEntity.class), any(CurrencyEntity.class)))
                .thenAnswer(inv -> (BigDecimal) inv.getArgument(0));

        this.service = new AccountServiceImpl(
                accountRepository,
                userService,
                iconRepository,
                mapper,
                currencyRepository,
                currencyRateUpdater
        );
    }

    @FuzzTest
    void fuzzAddAccount(FuzzedDataProvider data) {
        String name = data.consumeString(50);
        long iconId = data.consumeLong();
        String currencyCode = data.consumeString(5);

        AccountCreateRequest req = AccountCreateRequest.builder()
                .name(name)
                .iconId(iconId)
                .currencyCode(currencyCode)
                .build();

        try {
            service.addAccount(req, fixedPrincipal);
        } catch (EntityWithIdDoesntExistsException expected) {
        } catch (Throwable t) {
            throw t;
        }
    }

    @FuzzTest
    void fuzzGetAccountsByUser() {
        try {
            service.getAccountsByUser(fixedPrincipal);
        } catch (Throwable t) {
            throw t;
        }
    }

    @FuzzTest
    void fuzzGetAccountById(FuzzedDataProvider data) {
        long accountId = data.consumeLong();
        try {
            service.getAccountById(fixedPrincipal, accountId);
        } catch (EntityWithIdDoesntExistsException | AuthenticationException expected) {
        } catch (Throwable t) {
            throw t;
        }
    }

    @FuzzTest
    void fuzzUpdateAccount(FuzzedDataProvider data) {
        long accountId = data.consumeLong();
        String newName = data.consumeString(30);
        BigDecimal newBalance;
        try {
            double d = data.consumeDouble();
            newBalance = BigDecimal.valueOf(d);
        } catch (Exception e) {
            newBalance = BigDecimal.ZERO;
        }
        String newCurrency = data.consumeString(5);
        Long newIconId = data.consumeBoolean() ? data.consumeLong() : null;

        AccountUpdateRequest req = AccountUpdateRequest.builder()
                .name(newName)
                .balance(newBalance)
                .currencyCode(newCurrency)
                .iconId(newIconId)
                .build();

        try {
            service.updateAccount(accountId, req, fixedPrincipal);
        } catch (EntityWithIdDoesntExistsException | AuthenticationException expected) {
        } catch (Throwable t) {
            throw t;
        }
    }

    @FuzzTest
    void fuzzDeleteAccount(FuzzedDataProvider data) {
        long accountId = data.consumeLong();
        try {
            service.deleteAccount(accountId, fixedPrincipal);
        } catch (EntityWithIdDoesntExistsException | AuthenticationException expected) {
        } catch (Throwable t) {
            throw t;
        }
    }

    @FuzzTest
    void fuzzGetTotalBalance() {
        try {
            service.getTotalBalance(fixedPrincipal);
        } catch (Throwable t) {
            throw t;
        }
    }
}
