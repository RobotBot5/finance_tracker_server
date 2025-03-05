package com.robotbot.finance_tracker_server.services;

import com.robotbot.finance_tracker_server.domain.dto.UserRegisterRequest;
import com.robotbot.finance_tracker_server.domain.entities.UserEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

public interface UserService {

    Optional<UserEntity> getUserByEmail(String email);

    boolean isUserExistsByEmail(String email);

    void addUser(UserRegisterRequest userRegisterRequest, PasswordEncoder passwordEncoder);

}
