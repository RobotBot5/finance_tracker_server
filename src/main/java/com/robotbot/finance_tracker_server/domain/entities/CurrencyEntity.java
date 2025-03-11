package com.robotbot.finance_tracker_server.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "currencies")
public class CurrencyEntity {

    @Id
    @Column(nullable = false, unique = true, length = 3)
    private String code;

    @Column(length = 1)
    private String symbol;

    @Column(nullable = false)
    private Boolean target;

    private String name;
}
