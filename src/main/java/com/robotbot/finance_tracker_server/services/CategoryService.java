package com.robotbot.finance_tracker_server.services;

import com.robotbot.finance_tracker_server.domain.dto.CategoryCreateRequest;
import com.robotbot.finance_tracker_server.security.UserPrincipal;

public interface CategoryService {

    void addCategory(
            CategoryCreateRequest categoryCreateRequest,
            UserPrincipal userPrincipal
    );
}
