package com.robotbot.finance_tracker_server.domain.dto.authorize;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginResponse {

    private final String accessToken;

}
