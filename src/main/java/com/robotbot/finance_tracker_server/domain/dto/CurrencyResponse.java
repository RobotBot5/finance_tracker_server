package com.robotbot.finance_tracker_server.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CurrencyResponse {

    private String code;

    private String symbol;

    private String name;

}
