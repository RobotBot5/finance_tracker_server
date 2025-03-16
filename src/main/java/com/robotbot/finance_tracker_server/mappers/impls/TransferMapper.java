package com.robotbot.finance_tracker_server.mappers.impls;

import com.robotbot.finance_tracker_server.domain.dto.TransferCreateRequest;
import com.robotbot.finance_tracker_server.domain.entities.AccountEntity;
import com.robotbot.finance_tracker_server.domain.entities.TransferEntity;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;

@Component
public class TransferMapper {

    public TransferEntity mapRequestToEntity(
            TransferCreateRequest transferCreateRequest,
            AccountEntity accountFrom,
            AccountEntity accountTo
    ) {
        OffsetDateTime time;
        if (transferCreateRequest.getTime() == null) {
            time = OffsetDateTime.now();
        } else {
            time = OffsetDateTime.parse(transferCreateRequest.getTime());
        }
        return TransferEntity.builder()
                .amountFrom(transferCreateRequest.getAmountFrom())
                .amountTo(transferCreateRequest.getAmountTo())
                .accountFrom(accountFrom)
                .accountTo(accountTo)
                .time(time)
                .build();
    }
}
