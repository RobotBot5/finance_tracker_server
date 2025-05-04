package com.robotbot.finance_tracker_server.repositories;

import com.robotbot.finance_tracker_server.domain.entities.TransactionEntity;
import com.robotbot.finance_tracker_server.domain.entities.UserEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TransactionRepository extends CrudRepository<TransactionEntity, Long> {

    List<TransactionEntity> findByAccount_User(UserEntity user, Sort sort);
    List<TransactionEntity> findByAccount_UserAndDateBetween(UserEntity user, LocalDate startDate, LocalDate endDate);

    List<TransactionEntity> findByAccount_UserAndCategory_IsExpense(UserEntity user, Boolean isExpense, Sort sort);
    List<TransactionEntity> findByAccount_UserAndCategory_IsExpense(UserEntity user, Boolean isExpense);
}
