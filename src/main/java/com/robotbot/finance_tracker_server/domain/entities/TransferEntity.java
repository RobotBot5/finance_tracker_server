package com.robotbot.finance_tracker_server.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "transfers")
public class TransferEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition = "text")
    @Convert(converter = BigDecimalToStringConverter.class)
    private BigDecimal amountFrom;

    @Column(nullable = false, columnDefinition = "text")
    @Convert(converter = BigDecimalToStringConverter.class)
    private BigDecimal amountTo;

    @ManyToOne
    @JoinColumn(name = "account_from_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private AccountEntity accountFrom;

    @ManyToOne
    @JoinColumn(name = "account_to_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private AccountEntity accountTo;

    @Column(nullable = false)
    private OffsetDateTime time;
}
