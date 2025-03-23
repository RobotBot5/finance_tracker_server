package com.robotbot.finance_tracker_server.repositories;

import com.robotbot.finance_tracker_server.domain.entities.TransactionEntity;
import com.robotbot.finance_tracker_server.domain.entities.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.List;

@Repository
public interface TransactionRepository extends CrudRepository<TransactionEntity, Long> {

    List<TransactionEntity> findByAccount_User(UserEntity user);

    List<TransactionEntity> findByAccount_UserAndTimeBetween(UserEntity user, OffsetDateTime startDate, OffsetDateTime endDate);
}
