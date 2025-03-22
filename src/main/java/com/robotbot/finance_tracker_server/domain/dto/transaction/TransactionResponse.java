package com.robotbot.finance_tracker_server.domain.dto.transaction;

import com.robotbot.finance_tracker_server.domain.dto.account.AccountResponse;
import com.robotbot.finance_tracker_server.domain.dto.category.CategoryResponse;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Data
@Builder
public class TransactionResponse {

    private Long id;

    private BigDecimal amount;

    private OffsetDateTime time;

    private CategoryResponse category;

    private AccountResponse account;
}
