package com.robotbot.finance_tracker_server.controllers;

import com.robotbot.finance_tracker_server.domain.dto.transaction.TransactionCreateRequest;
import com.robotbot.finance_tracker_server.domain.dto.transaction.TransactionUpdateRequest;
import com.robotbot.finance_tracker_server.security.UserPrincipal;
import com.robotbot.finance_tracker_server.services.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping
    public ResponseEntity<Object> addTransaction(
            @RequestBody @Validated TransactionCreateRequest transactionCreateRequest,
            @AuthenticationPrincipal UserPrincipal userPrincipal
    ) {
        transactionService.addTransaction(transactionCreateRequest, userPrincipal);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<Object> getTransactions(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @RequestParam(value = "isExpense", required = false) Boolean isExpense,
            @RequestParam(value = "sortOrder", defaultValue = "asc") String sortOrder
    ) {
        return ResponseEntity.ok().body(transactionService.getTransactionsByUser(userPrincipal, isExpense, sortOrder));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getTransactionById(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @PathVariable(name = "id") Long id
    ) {
        return ResponseEntity.ok().body(transactionService.getTransactionById(userPrincipal, id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Object> updateTransaction(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @PathVariable(name = "id") Long id,
            @RequestBody @Validated TransactionUpdateRequest transactionUpdateRequest
    ) {
        transactionService.updateTransaction(id, transactionUpdateRequest, userPrincipal);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteTransaction(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @PathVariable(name = "id") Long id
    ) {
        transactionService.deleteTransaction(id, userPrincipal);
        return ResponseEntity.ok().build();
    }
}
