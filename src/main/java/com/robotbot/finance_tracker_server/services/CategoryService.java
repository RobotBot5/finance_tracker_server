package com.robotbot.finance_tracker_server.services;

import com.robotbot.finance_tracker_server.domain.dto.category.CategoriesResponse;
import com.robotbot.finance_tracker_server.domain.dto.category.CategoryCreateRequest;
import com.robotbot.finance_tracker_server.domain.dto.category.CategoryUpdateRequest;
import com.robotbot.finance_tracker_server.security.UserPrincipal;

public interface CategoryService {

    void addCategory(
            CategoryCreateRequest categoryCreateRequest,
            UserPrincipal userPrincipal
    );

    CategoriesResponse getCategoriesByUser(UserPrincipal userPrincipal);

    void updateCategory(
            Long categoryId,
            CategoryUpdateRequest categoryUpdateRequest,
            UserPrincipal userPrincipal
    );

    void deleteCategory(Long id, UserPrincipal userPrincipal);
}
