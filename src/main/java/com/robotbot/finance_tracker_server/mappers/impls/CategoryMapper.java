package com.robotbot.finance_tracker_server.mappers.impls;

import com.robotbot.finance_tracker_server.domain.dto.CategoriesResponse;
import com.robotbot.finance_tracker_server.domain.dto.CategoryCreateRequest;
import com.robotbot.finance_tracker_server.domain.dto.CategoryResponse;
import com.robotbot.finance_tracker_server.domain.entities.CategoryEntity;
import com.robotbot.finance_tracker_server.domain.entities.IconEntity;
import com.robotbot.finance_tracker_server.domain.entities.UserEntity;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CategoryMapper {

    private final ModelMapper mapper;

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

    public CategoryResponse mapEntityToResponse(CategoryEntity categoryEntity) {
        return mapper.map(categoryEntity, CategoryResponse.class);
    }

    public CategoriesResponse mapEntitiesListToResponse(List<CategoryEntity> categories) {
        return CategoriesResponse.builder()
                .categories(
                        categories
                                .stream()
                                .map(this::mapEntityToResponse)
                                .toList()
                ).build();
    }
}
