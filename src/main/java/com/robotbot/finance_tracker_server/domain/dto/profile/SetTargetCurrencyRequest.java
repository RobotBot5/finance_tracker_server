package com.robotbot.finance_tracker_server.domain.dto.profile;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SetTargetCurrencyRequest {

    @NotNull(message = "Currency id cannot be null")
    private String currencyId;
}
