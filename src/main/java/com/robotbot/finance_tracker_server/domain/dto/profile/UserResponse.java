package com.robotbot.finance_tracker_server.domain.dto.profile;

import com.robotbot.finance_tracker_server.domain.entities.CurrencyEntity;
import lombok.*;

@Data
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {

    private String email;
    private String firstName;
    private CurrencyEntity targetCurrency;
}
