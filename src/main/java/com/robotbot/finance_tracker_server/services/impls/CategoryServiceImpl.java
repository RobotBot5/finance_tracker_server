package com.robotbot.finance_tracker_server.services.impls;

import com.robotbot.finance_tracker_server.domain.dto.CategoriesResponse;
import com.robotbot.finance_tracker_server.domain.dto.CategoryCreateRequest;
import com.robotbot.finance_tracker_server.domain.dto.CategoryUpdateRequest;
import com.robotbot.finance_tracker_server.domain.entities.CategoryEntity;
import com.robotbot.finance_tracker_server.domain.entities.IconEntity;
import com.robotbot.finance_tracker_server.domain.entities.UserEntity;
import com.robotbot.finance_tracker_server.domain.exceptions.AuthenticationException;
import com.robotbot.finance_tracker_server.domain.exceptions.EntityWithIdDoesntExistsException;
import com.robotbot.finance_tracker_server.mappers.Mapper;
import com.robotbot.finance_tracker_server.mappers.impls.CategoryMapper;
import com.robotbot.finance_tracker_server.repositories.CategoryRepository;
import com.robotbot.finance_tracker_server.repositories.IconRepository;
import com.robotbot.finance_tracker_server.repositories.UserRepository;
import com.robotbot.finance_tracker_server.security.UserPrincipal;
import com.robotbot.finance_tracker_server.services.CategoryService;
import com.robotbot.finance_tracker_server.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper mapper;
    private final UserService userService;
    private final IconRepository iconRepository;

    @Override
    public void addCategory(
            CategoryCreateRequest categoryCreateRequest,
            UserPrincipal userPrincipal
    ) {
        UserEntity userEntity = userService.getUserByPrincipal(userPrincipal);
        IconEntity iconEntity = iconRepository
                .findById(categoryCreateRequest.getIconId())
                .orElseThrow(() -> new EntityWithIdDoesntExistsException("Icon not found"));
        CategoryEntity categoryEntity = mapper.mapRequestToEntity(categoryCreateRequest, userEntity, iconEntity);
        categoryRepository.save(categoryEntity);
    }

    @Override
    public CategoriesResponse getCategoriesByUser(UserPrincipal userPrincipal) {
        UserEntity userEntity = userService.getUserByPrincipal(userPrincipal);
        List<CategoryEntity> categories = categoryRepository.findByUser(userEntity);
        return mapper.mapEntitiesListToResponse(categories);
    }

    @Override
    public void updateCategory(
            Long categoryId,
            CategoryUpdateRequest categoryUpdateRequest,
            UserPrincipal userPrincipal
    ) {
        UserEntity userEntity = userService.getUserByPrincipal(userPrincipal);
        CategoryEntity category = categoryRepository
                .findById(categoryId)
                .orElseThrow(() -> new EntityWithIdDoesntExistsException("Category not found"));
        if (!category.getUser().getId().equals(userEntity.getId()) && !category.getIsSystem()) {
            throw new AuthenticationException();
        }
        if (categoryUpdateRequest.getName() != null) {
            category.setName(categoryUpdateRequest.getName());
        }

        if (categoryUpdateRequest.getIconId() != null) {
            IconEntity icon = iconRepository.findById(categoryUpdateRequest.getIconId())
                    .orElseThrow(() -> new EntityWithIdDoesntExistsException("Icon not found"));
            category.setIcon(icon);
        }

        categoryRepository.save(category);
    }
}
