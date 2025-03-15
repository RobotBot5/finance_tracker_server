package com.robotbot.finance_tracker_server.repositories;

import com.robotbot.finance_tracker_server.domain.entities.AccountEntity;
import com.robotbot.finance_tracker_server.domain.entities.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends CrudRepository<AccountEntity, Long> {

    List<AccountEntity> findByUser(UserEntity user);
}
