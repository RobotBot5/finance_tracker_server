package com.robotbot.finance_tracker_server.services.impls;

import com.robotbot.finance_tracker_server.domain.dto.TransferCreateRequest;
import com.robotbot.finance_tracker_server.domain.entities.AccountEntity;
import com.robotbot.finance_tracker_server.domain.entities.TransferEntity;
import com.robotbot.finance_tracker_server.domain.entities.UserEntity;
import com.robotbot.finance_tracker_server.domain.exceptions.AuthenticationException;
import com.robotbot.finance_tracker_server.domain.exceptions.TransferException;
import com.robotbot.finance_tracker_server.domain.exceptions.EntityWithIdDoesntExistsException;
import com.robotbot.finance_tracker_server.mappers.impls.TransferMapper;
import com.robotbot.finance_tracker_server.repositories.AccountRepository;
import com.robotbot.finance_tracker_server.repositories.TransferRepository;
import com.robotbot.finance_tracker_server.security.UserPrincipal;
import com.robotbot.finance_tracker_server.services.TransferService;
import com.robotbot.finance_tracker_server.services.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransferServiceImpl implements TransferService {

    private final TransferRepository transferRepository;
    private final UserService userService;
    private final AccountRepository accountRepository;
    private final CurrencyRateUpdater currencyRateUpdater;
    private final TransferMapper mapper;

    @Override
    @Transactional
    public void addTransfer(UserPrincipal userPrincipal, TransferCreateRequest transferCreateRequest) {
        UserEntity currentUser = userService.getUserByPrincipal(userPrincipal);

        AccountEntity accountFrom = accountRepository
                .findById(transferCreateRequest.getAccountFromId())
                .orElseThrow(() -> new EntityWithIdDoesntExistsException("Account from not found"));
        if (accountFrom.getUser() != currentUser) {
            throw new AuthenticationException("You are not allowed to get this account");
        }
        AccountEntity accountTo = accountRepository
                .findById(transferCreateRequest.getAccountToId())
                .orElseThrow(() -> new EntityWithIdDoesntExistsException("Account to not found"));
        if (accountTo.getUser() != currentUser) {
            throw new AuthenticationException("You are not allowed to get this account");
        }
        if (accountFrom.equals(accountTo)) {
            throw new TransferException("You can't transfer between same accounts");
        }
        if (accountFrom.getCurrency().equals(accountTo.getCurrency())) {
            if (transferCreateRequest.getAmountTo() != null && !transferCreateRequest.getAmountFrom().equals(transferCreateRequest.getAmountTo())) {
                throw new TransferException("You have same currencies, but different amounts");
            } else {
                transferCreateRequest.setAmountTo(transferCreateRequest.getAmountFrom());
            }
        } else {
            if (transferCreateRequest.getAmountTo() == null) {
                transferCreateRequest.setAmountTo(currencyRateUpdater.convert(
                        transferCreateRequest.getAmountFrom(),
                        accountFrom.getCurrency(),
                        accountTo.getCurrency()
                ));
            }
        }
        TransferEntity transfer = mapper.mapRequestToEntity(transferCreateRequest, accountFrom, accountTo);
        accountFrom.setBalance(accountFrom.getBalance().subtract(transfer.getAmountFrom()));
        accountRepository.save(accountFrom);
        accountTo.setBalance(accountTo.getBalance().add(transfer.getAmountTo()));
        accountRepository.save(accountTo);
        transferRepository.save(transfer);
    }
}
