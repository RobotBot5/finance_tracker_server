package com.robotbot.finance_tracker_server.services;

import com.robotbot.finance_tracker_server.domain.dto.IconsResponse;
import com.robotbot.finance_tracker_server.domain.entities.IconEntity;

public interface IconService {

    IconsResponse getIcons();

    IconEntity getIconById(Long id);
}
