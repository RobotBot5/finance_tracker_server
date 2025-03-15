package com.robotbot.finance_tracker_server.services;

import com.robotbot.finance_tracker_server.domain.dto.AccountCreateRequest;
import com.robotbot.finance_tracker_server.domain.dto.AccountUpdateRequest;
import com.robotbot.finance_tracker_server.domain.dto.AccountsResponse;
import com.robotbot.finance_tracker_server.security.UserPrincipal;

public interface AccountService {

    void addAccount(
            AccountCreateRequest accountCreateRequest,
            UserPrincipal userPrincipal
    );

    AccountsResponse getAccountsByUser(UserPrincipal userPrincipal);

    void updateAccount(
            Long accountId,
            AccountUpdateRequest accountUpdateRequest,
            UserPrincipal userPrincipal
    );

    void deleteAccount(Long id, UserPrincipal userPrincipal);
}
