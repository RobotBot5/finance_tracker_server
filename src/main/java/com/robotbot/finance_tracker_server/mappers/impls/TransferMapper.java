package com.robotbot.finance_tracker_server.mappers.impls;

import com.robotbot.finance_tracker_server.domain.dto.TransferCreateRequest;
import com.robotbot.finance_tracker_server.domain.entities.AccountEntity;
import com.robotbot.finance_tracker_server.domain.entities.TransferEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class TransferMapper {

    public TransferEntity mapRequestToEntity(
            TransferCreateRequest transferCreateRequest,
            AccountEntity accountFrom,
            AccountEntity accountTo
    ) {
        LocalDate date;
        if (transferCreateRequest.getDate() == null) {
            date = LocalDate.now();
        } else {
            date = LocalDate.parse(transferCreateRequest.getDate());
        }
        return TransferEntity.builder()
                .amountFrom(transferCreateRequest.getAmountFrom())
                .amountTo(transferCreateRequest.getAmountTo())
                .accountFrom(accountFrom)
                .accountTo(accountTo)
                .date(date)
                .build();
    }
}
