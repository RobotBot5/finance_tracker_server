package com.robotbot.finance_tracker_server.domain.dto.transaction;

import jakarta.validation.constraints.Pattern;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class TransactionUpdateRequest {

    private BigDecimal amount;

    @Pattern(
            regexp = "^\\d{4}-\\d{2}-\\d{2}$",
            message = "Date must be in ISO-8601 format, e.g. 2025-03-15"
    )
    private String date;

    private Long categoryId;

    private Long accountId;
}
