package com.robotbot.finance_tracker_server.repositories;

import com.robotbot.finance_tracker_server.domain.entities.CurrencyEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CurrencyRepository extends CrudRepository<CurrencyEntity, String> {

    CurrencyEntity findByTargetTrue();

    List<CurrencyEntity> findByTargetFalse();
}
