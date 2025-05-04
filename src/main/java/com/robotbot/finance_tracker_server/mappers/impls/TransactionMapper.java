package com.robotbot.finance_tracker_server.mappers.impls;

import com.robotbot.finance_tracker_server.domain.dto.transaction.TransactionCreateRequest;
import com.robotbot.finance_tracker_server.domain.dto.transaction.TransactionResponse;
import com.robotbot.finance_tracker_server.domain.dto.transaction.TransactionsResponse;
import com.robotbot.finance_tracker_server.domain.entities.AccountEntity;
import com.robotbot.finance_tracker_server.domain.entities.CategoryEntity;
import com.robotbot.finance_tracker_server.domain.entities.TransactionEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class TransactionMapper {

    private final AccountMapper accountMapper;
    private final CategoryMapper categoryMapper;

    public TransactionEntity mapRequestToEntity(
            TransactionCreateRequest transactionCreateRequest,
            CategoryEntity categoryEntity,
            AccountEntity accountEntity
    ) {
        LocalDate date;
        if (transactionCreateRequest.getDate() == null) {
            date = LocalDate.now();
        } else {
            date = LocalDate.parse(transactionCreateRequest.getDate());
        }
        return TransactionEntity.builder()
                .amount(transactionCreateRequest.getAmount())
                .date(date)
                .category(categoryEntity)
                .account(accountEntity)
                .build();
    }

    public TransactionResponse mapEntityToResponse(TransactionEntity transactionEntity) {
        return TransactionResponse.builder()
                .id(transactionEntity.getId())
                .amount(transactionEntity.getAmount())
                .date(transactionEntity.getDate())
                .category(categoryMapper.mapEntityToResponse(transactionEntity.getCategory()))
                .account(accountMapper.mapEntityToResponse(transactionEntity.getAccount()))
                .build();
    }

    public TransactionsResponse mapEntitiesToResponse(List<TransactionEntity> transactions) {
        return TransactionsResponse.builder()
                .transactions(
                        transactions.stream()
                                .map(this::mapEntityToResponse)
                                .toList()
                ).build();
    }
}
