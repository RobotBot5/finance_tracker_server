package com.robotbot.finance_tracker_server.services.impls;

import com.robotbot.finance_tracker_server.domain.dto.CategoryCreateRequest;
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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper mapper;
    private final UserRepository userRepository;
    private final IconRepository iconRepository;

    @Override
    public void addCategory(
            CategoryCreateRequest categoryCreateRequest,
            UserPrincipal userPrincipal
    ) {
        UserEntity userEntity = userRepository
                .findById(userPrincipal.getUserId())
                .orElseThrow(() -> new AuthenticationException("User not found"));
        IconEntity iconEntity = iconRepository
                .findById(categoryCreateRequest.getIconId())
                .orElseThrow(() -> new EntityWithIdDoesntExistsException("Icon not found"));
        CategoryEntity categoryEntity = mapper.mapRequestToEntity(categoryCreateRequest, userEntity, iconEntity);
        categoryRepository.save(categoryEntity);
    }
}
