package com.robotbot.finance_tracker_server.domain.dto;

import com.robotbot.finance_tracker_server.domain.entities.IconEntity;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class IconsResponse {

    private final List<IconEntity> icons;
}
