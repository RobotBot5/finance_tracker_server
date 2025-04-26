package com.robotbot.finance_tracker_server.services.impls;

import com.robotbot.finance_tracker_server.domain.dto.IconsResponse;
import com.robotbot.finance_tracker_server.domain.entities.IconEntity;
import com.robotbot.finance_tracker_server.domain.exceptions.EntityWithIdDoesntExistsException;
import com.robotbot.finance_tracker_server.mappers.impls.IconMapper;
import com.robotbot.finance_tracker_server.repositories.IconRepository;
import com.robotbot.finance_tracker_server.services.IconService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class IconServiceImpl implements IconService {

    private final IconRepository iconRepository;
    private final IconMapper iconMapper;

    @Override
    public IconsResponse getIcons() {
        return iconMapper.mapEntityToDto(StreamSupport.stream(
                iconRepository.findAll().spliterator(),
                false
        ).collect(Collectors.toList()));
    }

    @Override
    public IconEntity getIconById(Long id) {
        return iconRepository
                .findById(id)
                .orElseThrow(() -> new EntityWithIdDoesntExistsException("Icon not found"));
    }
}
