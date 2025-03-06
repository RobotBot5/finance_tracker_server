package com.robotbot.finance_tracker_server.mappers.impls;

import com.robotbot.finance_tracker_server.domain.dto.IconsResponse;
import com.robotbot.finance_tracker_server.domain.entities.IconEntity;
import com.robotbot.finance_tracker_server.mappers.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class IconMapper implements Mapper<IconsResponse, List<IconEntity>> {

    @Override
    public List<IconEntity> mapDtoToEntity(IconsResponse iconsResponse) {
        return null;
    }

    @Override
    public IconsResponse mapEntityToDto(List<IconEntity> iconEntities) {
        return IconsResponse.builder().icons(iconEntities).build();
    }
}
