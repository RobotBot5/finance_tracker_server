package com.robotbot.finance_tracker_server.domain.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class TransferCreateRequest {

    @NotNull(message = "Amount from cannot be null")
    private BigDecimal amountFrom;

    private BigDecimal amountTo;

    @NotNull(message = "From account id cannot be null")
    private Long accountFromId;

    @NotNull(message = "To account id cannot be null")
    private Long accountToId;

    @Pattern(
            regexp = "^\\d{4}-\\d{2}-\\d{2}$",
            message = "Date must be in ISO-8601 format, e.g. 2025-03-15"
    )
    private String date;

}
