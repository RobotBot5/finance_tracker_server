package com.robotbot.finance_tracker_server.services.impls;

import com.robotbot.finance_tracker_server.domain.dto.profile.UserResponse;
import com.robotbot.finance_tracker_server.domain.entities.UserEntity;
import com.robotbot.finance_tracker_server.mappers.impls.UserMapper;
import com.robotbot.finance_tracker_server.repositories.CurrencyRepository;
import com.robotbot.finance_tracker_server.repositories.UserRepository;
import com.robotbot.finance_tracker_server.security.UserPrincipal;
import com.robotbot.finance_tracker_server.services.ProfileService;
import com.robotbot.finance_tracker_server.services.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final UserService userService;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final CurrencyRepository currencyRepository;

    @Override
    public UserResponse getUserProfile(UserPrincipal userPrincipal) {
        UserEntity user = userService.getUserByPrincipal(userPrincipal);
        return userMapper.mapUserToResponse(user);
    }

    @Transactional
    @Override
    public void setTargetCurrency(String currencyId, UserPrincipal userPrincipal) {
        UserEntity user = userService.getUserByPrincipal(userPrincipal);
        user.setTargetCurrency(currencyRepository.findByCode(currencyId));
        userRepository.save(user);
    }
}
