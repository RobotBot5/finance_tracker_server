package com.robotbot.finance_tracker_server.controllers;

import com.robotbot.finance_tracker_server.domain.dto.profile.SetTargetCurrencyRequest;
import com.robotbot.finance_tracker_server.domain.dto.profile.UserResponse;
import com.robotbot.finance_tracker_server.security.UserPrincipal;
import com.robotbot.finance_tracker_server.services.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    @GetMapping
    public ResponseEntity<UserResponse> getProfile(
            @AuthenticationPrincipal UserPrincipal userPrincipal) {
        return ResponseEntity.ok(profileService.getUserProfile(userPrincipal));
    }

    @PatchMapping("/target-currency")
    public ResponseEntity updateTargetCurrency(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @RequestBody @Validated SetTargetCurrencyRequest request) {
        profileService.setTargetCurrency(request.getCurrencyId(), userPrincipal);
        return ResponseEntity.ok().build();
    }
}
