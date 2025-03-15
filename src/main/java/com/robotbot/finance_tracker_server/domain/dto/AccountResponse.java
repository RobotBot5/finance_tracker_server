package com.robotbot.finance_tracker_server.domain.dto;

import com.robotbot.finance_tracker_server.domain.entities.IconEntity;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
@Builder
public class AccountResponse {

    private Long id;

    private String name;

    private CurrencyResponse currency;

    private IconEntity icon;
}
