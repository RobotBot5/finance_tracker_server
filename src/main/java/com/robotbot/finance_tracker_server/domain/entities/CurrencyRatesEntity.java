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

    // Это поле является первичным ключом
    @Id
    @Column(name = "currency_code", nullable = false, length = 3)
    private String currencyCode;

    // Связь, которая использует идентификатор из currencyCode
    @OneToOne(cascade = CascadeType.REMOVE)
    @MapsId  // Указывает, что первичный ключ берется из поля currencyCode
    @JoinColumn(name = "currency_code", nullable = false)
    private CurrencyEntity currency;

    @Column(nullable = false)
    private String rate;

    @Column(name = "last_updated", nullable = false)
    private Instant lastUpdated;
}
