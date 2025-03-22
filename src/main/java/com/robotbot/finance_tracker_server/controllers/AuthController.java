package com.robotbot.finance_tracker_server.controllers;

import com.robotbot.finance_tracker_server.domain.dto.authorize.LoginRequest;
import com.robotbot.finance_tracker_server.domain.dto.authorize.LoginResponse;
import com.robotbot.finance_tracker_server.domain.dto.authorize.UserRegisterRequest;
import com.robotbot.finance_tracker_server.services.AuthService;
import com.robotbot.finance_tracker_server.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public LoginResponse login(@RequestBody @Validated LoginRequest request) {
        return authService.attemptLogin(request.getEmail(), request.getPassword());
    }

    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody @Validated UserRegisterRequest userRegisterRequest) {
        userService.addUser(userRegisterRequest, passwordEncoder);
        return ResponseEntity.ok().build();
    }

}
