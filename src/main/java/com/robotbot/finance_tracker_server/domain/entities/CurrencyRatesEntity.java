package com.robotbot.finance_tracker_server.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "currency_rates")
public class CurrencyRatesEntity {

    @Id
    @Column(name = "currency_code", nullable = false, length = 3)
    private String currencyCode;

    @OneToOne(cascade = CascadeType.REMOVE)
    @MapsId
    @JoinColumn(name = "currency_code", nullable = false)
    private CurrencyEntity currency;

    @Column(nullable = false)
    private String rate;

    @Column(name = "last_updated", nullable = false)
    private Instant lastUpdated;
}
