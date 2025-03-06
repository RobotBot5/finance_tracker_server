package com.robotbot.finance_tracker_server.repositories;

import com.robotbot.finance_tracker_server.domain.entities.IconEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IconRepository extends CrudRepository<IconEntity, Long> {

}
