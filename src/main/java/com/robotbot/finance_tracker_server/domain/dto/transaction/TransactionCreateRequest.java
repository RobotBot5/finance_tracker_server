package com.robotbot.finance_tracker_server.domain.dto.transaction;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class TransactionCreateRequest {

    @NotNull(message = "Amount cannot be null")
    private BigDecimal amount;

    @Pattern(
            regexp = "^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}\\.\\d{3}[+-]\\d{2}:\\d{2}$",
            message = "Time must be in ISO-8601 format, e.g. 2025-03-15T10:15:30.000+03:00"
    )
    private String time;

    @NotNull(message = "Category id cannot be null")
    private Long categoryId;

    @NotNull(message = "Account id cannot be null")
    private Long accountId;
}
