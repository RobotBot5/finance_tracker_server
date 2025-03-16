package com.robotbot.finance_tracker_server.services;

import com.robotbot.finance_tracker_server.domain.dto.TransactionCreateRequest;
import com.robotbot.finance_tracker_server.domain.dto.TransactionUpdateRequest;
import com.robotbot.finance_tracker_server.domain.dto.TransactionsResponse;
import com.robotbot.finance_tracker_server.security.UserPrincipal;

public interface TransactionService {

    void addTransaction(
            TransactionCreateRequest transactionCreateRequest,
            UserPrincipal userPrincipal
    );

    TransactionsResponse getTransactionsByUser(UserPrincipal userPrincipal);

    void updateTransaction(
            Long transactionId,
            TransactionUpdateRequest transactionUpdateRequest,
            UserPrincipal userPrincipal
    );

    void deleteTransaction(Long id, UserPrincipal userPrincipal);
}
