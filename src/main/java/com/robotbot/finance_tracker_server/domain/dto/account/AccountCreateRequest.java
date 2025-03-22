package com.robotbot.finance_tracker_server.domain.dto.account;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class AccountCreateRequest {

    @NotBlank(message = "Name cannot be null")
    private String name;

    @NotBlank(message = "Currency code cannot be null")
    private String currencyCode;

    private BigDecimal balance;

    @NotNull(message = "Icon id cannot be null")
    private Long iconId;

}
