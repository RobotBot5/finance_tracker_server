package com.robotbot.finance_tracker_server.services;

import com.robotbot.finance_tracker_server.domain.dto.LoginResponse;

public interface AuthService {

    LoginResponse attemptLogin(String email, String password);

}
