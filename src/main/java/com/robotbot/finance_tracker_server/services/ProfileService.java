package com.robotbot.finance_tracker_server.services;

import com.robotbot.finance_tracker_server.domain.dto.profile.UserResponse;
import com.robotbot.finance_tracker_server.security.UserPrincipal;

public interface ProfileService {

    UserResponse getUserProfile(UserPrincipal userPrincipal);

    void setTargetCurrency(String currencyId, UserPrincipal userPrincipal);
}
