package com.robotbot.finance_tracker_server.repositories;

import com.robotbot.finance_tracker_server.domain.entities.CurrencyEntity;
import com.robotbot.finance_tracker_server.domain.entities.CurrencyRatesEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CurrencyRatesRepository extends CrudRepository<CurrencyRatesEntity, CurrencyEntity> {

    Optional<CurrencyRatesEntity> findByCurrency(CurrencyEntity currency);
}
