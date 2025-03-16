package com.robotbot.finance_tracker_server.controllers;

import com.robotbot.finance_tracker_server.domain.dto.AccountCreateRequest;
import com.robotbot.finance_tracker_server.domain.dto.AccountUpdateRequest;
import com.robotbot.finance_tracker_server.domain.dto.TransferCreateRequest;
import com.robotbot.finance_tracker_server.security.UserPrincipal;
import com.robotbot.finance_tracker_server.services.AccountService;
import com.robotbot.finance_tracker_server.services.TransferService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
public class AccountsController {

    private final AccountService accountService;
    private final TransferService transferService;

    @PostMapping
    public ResponseEntity<Object> createAccount(
            @RequestBody @Validated AccountCreateRequest accountCreateRequest,
            @AuthenticationPrincipal UserPrincipal userPrincipal
    ) {
        accountService.addAccount(accountCreateRequest, userPrincipal);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<Object> getAccounts(
            @AuthenticationPrincipal UserPrincipal userPrincipal
    ) {
        return ResponseEntity.ok().body(accountService.getAccountsByUser(userPrincipal));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Object> updateAccount(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @PathVariable Long id,
            @RequestBody AccountUpdateRequest accountUpdateRequest
    ) {
        accountService.updateAccount(id, accountUpdateRequest, userPrincipal);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteAccount(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @PathVariable Long id
    ) {
        accountService.deleteAccount(id, userPrincipal);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/transfer")
    public ResponseEntity<Object> transferBetweenAccounts(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @RequestBody @Validated TransferCreateRequest transferCreateRequest
            ) {
        transferService.addTransfer(userPrincipal, transferCreateRequest);
        return ResponseEntity.ok().build();
    }
}
