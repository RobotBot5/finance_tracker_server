package com.robotbot.finance_tracker_server.mappers.impls;

import com.robotbot.finance_tracker_server.domain.dto.CategoryCreateRequest;
import com.robotbot.finance_tracker_server.domain.entities.CategoryEntity;
import com.robotbot.finance_tracker_server.domain.entities.IconEntity;
import com.robotbot.finance_tracker_server.domain.entities.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    public CategoryEntity mapRequestToEntity(
            CategoryCreateRequest categoryCreateRequest,
            UserEntity userEntity,
            IconEntity iconEntity
    ) {
        return CategoryEntity.builder()
                .name(categoryCreateRequest.getName())
                .user(userEntity)
                .icon(iconEntity)
                .isExpense(categoryCreateRequest.getIsExpense())
                .isSystem(false)
                .build();
    }
}
