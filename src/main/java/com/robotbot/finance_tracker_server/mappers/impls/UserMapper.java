package com.robotbot.finance_tracker_server.mappers.impls;

import com.robotbot.finance_tracker_server.domain.dto.UserRegisterRequest;
import com.robotbot.finance_tracker_server.domain.entities.UserEntity;
import com.robotbot.finance_tracker_server.mappers.Mapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper implements Mapper<UserRegisterRequest, UserEntity> {

    private final ModelMapper mapper;

    @Override
    public UserEntity mapDtoToEntity(UserRegisterRequest userRegisterRequest) {
        return mapper.map(userRegisterRequest, UserEntity.class);
    }

    @Override
    public UserRegisterRequest mapEntityToDto(UserEntity userEntity) {
        return null;
    }
}
