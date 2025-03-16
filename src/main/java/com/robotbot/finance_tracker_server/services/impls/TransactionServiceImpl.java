package com.robotbot.finance_tracker_server.services.impls;

import com.robotbot.finance_tracker_server.domain.dto.TransactionCreateRequest;
import com.robotbot.finance_tracker_server.domain.dto.TransactionUpdateRequest;
import com.robotbot.finance_tracker_server.domain.dto.TransactionsResponse;
import com.robotbot.finance_tracker_server.domain.entities.*;
import com.robotbot.finance_tracker_server.domain.exceptions.AuthenticationException;
import com.robotbot.finance_tracker_server.domain.exceptions.CategoryExpenseAndIncomeException;
import com.robotbot.finance_tracker_server.domain.exceptions.EntityWithIdDoesntExistsException;
import com.robotbot.finance_tracker_server.mappers.impls.TransactionMapper;
import com.robotbot.finance_tracker_server.repositories.AccountRepository;
import com.robotbot.finance_tracker_server.repositories.CategoryRepository;
import com.robotbot.finance_tracker_server.repositories.TransactionRepository;
import com.robotbot.finance_tracker_server.security.UserPrincipal;
import com.robotbot.finance_tracker_server.services.TransactionService;
import com.robotbot.finance_tracker_server.services.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final TransactionMapper mapper;
    private final UserService userService;
    private final AccountRepository accountRepository;
    private final CategoryRepository categoryRepository;


    @Override
    @Transactional
    public void addTransaction(TransactionCreateRequest transactionCreateRequest, UserPrincipal userPrincipal) {
        UserEntity currentUser = userService.getUserByPrincipal(userPrincipal);

        CategoryEntity categoryEntity = categoryRepository
                .findById(transactionCreateRequest.getCategoryId())
                .orElseThrow(() -> new EntityWithIdDoesntExistsException("Category not found"));
        if (categoryEntity.getUser() != null && categoryEntity.getUser() != currentUser) {
            throw new AuthenticationException("You are not allowed to get this category");
        }
        AccountEntity accountEntity = accountRepository
                .findById(transactionCreateRequest.getAccountId())
                .orElseThrow(() -> new EntityWithIdDoesntExistsException("Account not found"));
        if (accountEntity.getUser() != currentUser) {
            throw new AuthenticationException("You are not allowed to get this account");
        }
        TransactionEntity transactionEntity = mapper.mapRequestToEntity(
                transactionCreateRequest,
                categoryEntity,
                accountEntity
        );
        if (categoryEntity.getIsExpense()) {
            accountEntity.setBalance(accountEntity.getBalance().subtract(transactionEntity.getAmount()));
        } else {
            accountEntity.setBalance(accountEntity.getBalance().add(transactionEntity.getAmount()));
        }
        accountRepository.save(accountEntity);
        transactionRepository.save(transactionEntity);
    }

    @Override
    public TransactionsResponse getTransactionsByUser(UserPrincipal userPrincipal) {
        UserEntity userEntity = userService.getUserByPrincipal(userPrincipal);
        List<TransactionEntity> transactions = transactionRepository.findByAccount_User(userEntity);
        return mapper.mapEntitiesToResponse(transactions);
    }

    @Override
    @Transactional
    public void updateTransaction(Long transactionId, TransactionUpdateRequest transactionUpdateRequest, UserPrincipal userPrincipal) {
        TransactionEntity transaction = getTransactionEntity(transactionId, userPrincipal);

        if (transactionUpdateRequest.getAmount() != null) {
            BigDecimal oldAmount = transaction.getAmount();
            transaction.setAmount(transactionUpdateRequest.getAmount());
            BigDecimal differenceBetweenAmounts = transaction.getAmount().subtract(oldAmount);
            AccountEntity account = transaction.getAccount();
            if (transaction.getCategory().getIsExpense()) {
                account.setBalance(account.getBalance().subtract(differenceBetweenAmounts));
            } else {
                account.setBalance(account.getBalance().add(differenceBetweenAmounts));
            }
            accountRepository.save(account);
        }

        if (transactionUpdateRequest.getTime() != null) {
            transaction.setTime(OffsetDateTime.parse(transactionUpdateRequest.getTime()));
        }

        UserEntity currentUser = userService.getUserByPrincipal(userPrincipal);

        if (transactionUpdateRequest.getCategoryId() != null) {
            CategoryEntity category = categoryRepository.findById(transactionUpdateRequest.getCategoryId())
                    .orElseThrow(() -> new EntityWithIdDoesntExistsException("Category not found"));
            if (category.getUser() != null && category.getUser() != currentUser) {
                throw new AuthenticationException("You are not allowed to get this category");
            }
            if (category.getIsExpense() != transaction.getCategory().getIsExpense()) {
                throw new CategoryExpenseAndIncomeException("Different expense status in categories");
            }
            transaction.setCategory(category);
        }

        if (transactionUpdateRequest.getAccountId() != null) {
            AccountEntity account = accountRepository.findById(transactionUpdateRequest.getAccountId())
                    .orElseThrow(() -> new EntityWithIdDoesntExistsException("Account not found"));
            if (account.getUser() != currentUser) {
                throw new AuthenticationException("You are not allowed to get this account");
            }
            transaction.setAccount(account);
        }

        transactionRepository.save(transaction);
    }

    @Override
    @Transactional
    public void deleteTransaction(Long id, UserPrincipal userPrincipal) {
        TransactionEntity transaction = getTransactionEntity(id, userPrincipal);
        AccountEntity account = transaction.getAccount();
        if (transaction.getCategory().getIsExpense()) {
            account.setBalance(account.getBalance().add(transaction.getAmount()));
        } else {
            account.setBalance(account.getBalance().subtract(transaction.getAmount()));
        }
        accountRepository.save(account);
        transactionRepository.delete(transaction);
    }

    private TransactionEntity getTransactionEntity(Long id, UserPrincipal userPrincipal) {
        UserEntity userEntity = userService.getUserByPrincipal(userPrincipal);
        TransactionEntity transaction = transactionRepository
                .findById(id)
                .orElseThrow(() -> new EntityWithIdDoesntExistsException("Transaction not found"));
        if (!transaction.getAccount().getUser().getId().equals(userEntity.getId())) {
            throw new AuthenticationException();
        }
        return transaction;
    }
}
