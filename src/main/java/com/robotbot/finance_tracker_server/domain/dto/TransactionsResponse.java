package com.robotbot.finance_tracker_server.domain.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class TransactionsResponse {

    private List<TransactionResponse> transactions;
}
