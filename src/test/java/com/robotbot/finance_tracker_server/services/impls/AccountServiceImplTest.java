package com.robotbot.finance_tracker_server.services.impls;

import com.robotbot.finance_tracker_server.domain.dto.account.AccountCreateRequest;
import com.robotbot.finance_tracker_server.domain.dto.account.AccountResponse;
import com.robotbot.finance_tracker_server.domain.dto.account.AccountUpdateRequest;
import com.robotbot.finance_tracker_server.domain.dto.account.AccountsResponse;
import com.robotbot.finance_tracker_server.domain.dto.currency.CurrencyResponse;
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
import com.robotbot.finance_tracker_server.services.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AccountServiceImplTest {

    @InjectMocks
    private AccountServiceImpl accountService;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private UserService userService;

    @Mock
    private IconRepository iconRepository;

    @Mock
    private AccountMapper mapper;

    @Mock
    private CurrencyRepository currencyRepository;

    private UserEntity userEntity;
    private UserPrincipal userPrincipal;
    private IconEntity iconEntity;
    private CurrencyEntity currencyEntity;
    private AccountEntity accountEntity;
    private AccountCreateRequest accountCreateRequest;
    private AccountUpdateRequest accountUpdateRequest;
    private AccountsResponse accountsResponse;
    private AccountResponse accountResponse;
    private CurrencyResponse currencyResponse;

    @Before
    public void setUp() {
        userEntity = new UserEntity();
        userEntity.setId(1L);
        userPrincipal = UserPrincipal.builder()
                .userId(1L)
                .email("test@test.com")
                .password("password")
                .build();

        iconEntity = IconEntity.builder()
                .id(1L)
                .name("testIcon")
                .path("/icons/test.png")
                .build();

        currencyEntity = CurrencyEntity.builder()
                .code("USD")
                .symbol("$")
                .target(true)
                .build();

        accountEntity = AccountEntity.builder()
                .id(1L)
                .user(userEntity)
                .name("test account")
                .balance(BigDecimal.valueOf(1000))
                .icon(iconEntity)
                .currency(currencyEntity)
                .build();

        accountCreateRequest = AccountCreateRequest.builder()
                .name("Test Account")
                .iconId(1L)
                .currencyCode("USD")
                .build();

        accountUpdateRequest = AccountUpdateRequest.builder()
                .name("Updated Account")
                .balance(BigDecimal.valueOf(500))
                .iconId(2L)
                .currencyCode("OLOLO")
                .build();

        currencyResponse = new CurrencyResponse("USD", "$", "dollars");

        accountResponse = AccountResponse.builder()
                .id(1L)
                .name("Test name")
                .currency(currencyResponse)
                .balance(BigDecimal.valueOf(1000))
                .icon(iconEntity)
                .build();

        accountsResponse = AccountsResponse.builder()
                .accounts(List.of(accountResponse))
                .build();
    }

    @Test
    public void testAddAccount_Success() {
        when(userService.getUserByPrincipal(userPrincipal)).thenReturn(userEntity);
        when(iconRepository.findById(accountCreateRequest.getIconId())).thenReturn(Optional.of(iconEntity));
        when(currencyRepository.findById(accountCreateRequest.getCurrencyCode())).thenReturn(Optional.of(currencyEntity));

        AccountEntity mappedAccount = AccountEntity.builder()
                .id(1L)
                .name("Test Account")
                .user(userEntity)
                .icon(iconEntity)
                .currency(currencyEntity)
                .balance(BigDecimal.ZERO)
                .build();
        when(mapper.mapRequestToEntity(accountCreateRequest, userEntity, iconEntity, currencyEntity))
                .thenReturn(mappedAccount);

        accountService.addAccount(accountCreateRequest, userPrincipal);

        verify(accountRepository, times(1)).save(mappedAccount);
    }

    @Test
    public void testAddAccount_IconNotFound() {
        when(userService.getUserByPrincipal(userPrincipal)).thenReturn(userEntity);
        when(iconRepository.findById(accountCreateRequest.getIconId())).thenReturn(Optional.empty());

        try {
            accountService.addAccount(accountCreateRequest, userPrincipal);
            fail("Expected EntityWithIdDoesntExistsException");
        } catch (EntityWithIdDoesntExistsException ex) {
            assertEquals("Icon not found", ex.getMessage());
        }
    }

    @Test
    public void testAddAccount_CurrencyNotFound() {
        when(userService.getUserByPrincipal(userPrincipal)).thenReturn(userEntity);
        when(iconRepository.findById(accountCreateRequest.getIconId())).thenReturn(Optional.of(iconEntity));
        when(currencyRepository.findById(accountCreateRequest.getCurrencyCode())).thenReturn(Optional.empty());

        try {
            accountService.addAccount(accountCreateRequest, userPrincipal);
            fail("Expected EntityWithIdDoesntExistsException");
        } catch (EntityWithIdDoesntExistsException ex) {
            assertEquals("Currency not found", ex.getMessage());
        }
    }

    @Test
    public void testGetAccountsByUser_Success() {
        when(userService.getUserByPrincipal(userPrincipal)).thenReturn(userEntity);
        List<AccountEntity> accounts = List.of(accountEntity);
        when(accountRepository.findByUser(userEntity)).thenReturn(accounts);
        when(mapper.mapEntitiesListToResponse(accounts)).thenReturn(accountsResponse);

        AccountsResponse result = accountService.getAccountsByUser(userPrincipal);

        assertSame(accountsResponse, result);
    }

    @Test
    public void testUpdateAccount_Success() {
        CurrencyEntity updatedCurrencyEntity = CurrencyEntity.builder()
                .code(accountUpdateRequest.getCurrencyCode())
                .name("ALALA")
                .symbol("Ы")
                .target(false)
                .build();
        IconEntity updatedIconEntity = IconEntity.builder()
                .id(accountUpdateRequest.getIconId())
                .name("ALALALALA")
                .path("DSADA")
                .build();
        when(userService.getUserByPrincipal(userPrincipal)).thenReturn(userEntity);
        when(accountRepository.findById(1L)).thenReturn(Optional.of(accountEntity));
        when(currencyRepository.findByCode(accountUpdateRequest.getCurrencyCode())).thenReturn(updatedCurrencyEntity);
        when(iconRepository.findById(accountUpdateRequest.getIconId())).thenReturn(Optional.of(updatedIconEntity));

        accountService.updateAccount(1L, accountUpdateRequest, userPrincipal);

        assertEquals(accountUpdateRequest.getName(), accountEntity.getName());
        assertEquals(accountUpdateRequest.getBalance(), accountEntity.getBalance());
        assertEquals(updatedCurrencyEntity, accountEntity.getCurrency());
        assertEquals(updatedIconEntity, accountEntity.getIcon());
        verify(accountRepository, times(1)).save(accountEntity);
    }

    @Test
    public void testUpdateAccount_CurrencyNotFound() {
        when(userService.getUserByPrincipal(userPrincipal)).thenReturn(userEntity);
        when(accountRepository.findById(1L)).thenReturn(Optional.of(accountEntity));
        when(currencyRepository.findByCode(accountUpdateRequest.getCurrencyCode())).thenReturn(null);

        try {
            accountService.updateAccount(1L, accountUpdateRequest, userPrincipal);
            fail("Expected EntityWithIdDoesntExistsException");
        } catch (EntityWithIdDoesntExistsException ex) {
            assertEquals("Currency not found", ex.getMessage());
        }
    }

    @Test
    public void testUpdateAccount_IconNotFound() {
        CurrencyEntity updatedCurrencyEntity = CurrencyEntity.builder()
                .code(accountUpdateRequest.getCurrencyCode())
                .name("ALALA")
                .symbol("Ы")
                .target(false)
                .build();
        when(userService.getUserByPrincipal(userPrincipal)).thenReturn(userEntity);
        when(accountRepository.findById(1L)).thenReturn(Optional.of(accountEntity));
        when(currencyRepository.findByCode(accountUpdateRequest.getCurrencyCode())).thenReturn(updatedCurrencyEntity);
        when(iconRepository.findById(accountUpdateRequest.getIconId())).thenReturn(Optional.empty());

        try {
            accountService.updateAccount(1L, accountUpdateRequest, userPrincipal);
            fail("Expected EntityWithIdDoesntExistsException");
        } catch (EntityWithIdDoesntExistsException ex) {
            assertEquals("Icon not found", ex.getMessage());
        }
    }

    @Test
    public void testDeleteAccount_Success() {
        when(userService.getUserByPrincipal(userPrincipal)).thenReturn(userEntity);
        when(accountRepository.findById(1L)).thenReturn(Optional.of(accountEntity));

        accountService.deleteAccount(1L, userPrincipal);

        verify(accountRepository, times(1)).delete(accountEntity);
    }

    @Test
    public void testGetAccountEntityInUpdate_AuthenticationException() {
        UserEntity anotherUser = UserEntity.builder().id(2L).build();
        accountEntity.setUser(anotherUser);

        when(userService.getUserByPrincipal(userPrincipal)).thenReturn(userEntity);
        when(accountRepository.findById(1L)).thenReturn(Optional.of(accountEntity));

        try {
            accountService.updateAccount(1L, accountUpdateRequest, userPrincipal);
            fail("Expected AuthenticationException");
        } catch (AuthenticationException ex) {
            testPassed();
        }
    }

    @Test
    public void testGetAccountEntityInDelete_AuthenticationException() {
        UserEntity anotherUser = UserEntity.builder().id(2L).build();
        accountEntity.setUser(anotherUser);

        when(userService.getUserByPrincipal(userPrincipal)).thenReturn(userEntity);
        when(accountRepository.findById(1L)).thenReturn(Optional.of(accountEntity));

        try {
            accountService.deleteAccount(1L, userPrincipal);
            fail("Expected AuthenticationException");
        } catch (AuthenticationException ex) {
            testPassed();
        }
    }

    private void testPassed() {}
}
