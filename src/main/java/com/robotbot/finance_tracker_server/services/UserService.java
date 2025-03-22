package com.robotbot.finance_tracker_server.services;

import com.robotbot.finance_tracker_server.domain.dto.authorize.UserRegisterRequest;
import com.robotbot.finance_tracker_server.domain.entities.UserEntity;
import com.robotbot.finance_tracker_server.security.UserPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

public interface UserService {

    Optional<UserEntity> getUserByEmail(String email);

    boolean isUserExistsByEmail(String email);

    void addUser(UserRegisterRequest userRegisterRequest, PasswordEncoder passwordEncoder);

    UserEntity getUserByPrincipal(UserPrincipal userPrincipal);

}
