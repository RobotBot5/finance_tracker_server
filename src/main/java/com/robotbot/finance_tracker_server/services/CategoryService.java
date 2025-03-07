package com.robotbot.finance_tracker_server.services;

import com.robotbot.finance_tracker_server.domain.dto.CategoriesResponse;
import com.robotbot.finance_tracker_server.domain.dto.CategoryCreateRequest;
import com.robotbot.finance_tracker_server.domain.entities.CategoryEntity;
import com.robotbot.finance_tracker_server.security.UserPrincipal;

import java.util.List;

public interface CategoryService {

    void addCategory(
            CategoryCreateRequest categoryCreateRequest,
            UserPrincipal userPrincipal
    );

    CategoriesResponse getCategoriesByUser(UserPrincipal userPrincipal);
}
