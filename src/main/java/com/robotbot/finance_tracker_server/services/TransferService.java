package com.robotbot.finance_tracker_server.services;

import com.robotbot.finance_tracker_server.domain.dto.TransferCreateRequest;
import com.robotbot.finance_tracker_server.security.UserPrincipal;

public interface TransferService {

    void addTransfer(
            UserPrincipal userPrincipal,
            TransferCreateRequest transferCreateRequest
    );
}
