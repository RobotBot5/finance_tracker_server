package com.robotbot.finance_tracker_server.services.impls;

import com.robotbot.finance_tracker_server.domain.dto.UserRegisterRequest;
import com.robotbot.finance_tracker_server.domain.entities.UserEntity;
import com.robotbot.finance_tracker_server.domain.exceptions.EmailAlreadyExistsException;
import com.robotbot.finance_tracker_server.mappers.Mapper;
import com.robotbot.finance_tracker_server.repositories.UserRepository;
import com.robotbot.finance_tracker_server.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final Mapper<UserRegisterRequest, UserEntity> mapper;

    @Override
    public Optional<UserEntity> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public boolean isUserExistsByEmail(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    @Override
    public void addUser(UserRegisterRequest userRegisterRequest, PasswordEncoder passwordEncoder) {
        if (isUserExistsByEmail(userRegisterRequest.getEmail())) {
            throw new EmailAlreadyExistsException("User with this email already exists");
        }
        UserEntity userEntity = mapper.mapDtoToEntity(userRegisterRequest);
        userEntity.setRole("ROLE_USER");
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        userRepository.save(userEntity);
    }
}
