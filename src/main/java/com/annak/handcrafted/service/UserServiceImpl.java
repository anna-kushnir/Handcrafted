package com.annak.handcrafted.service;

import com.annak.handcrafted.dto.UserDto;
import com.annak.handcrafted.entity.Role;
import com.annak.handcrafted.entity.User;
import com.annak.handcrafted.exception.ResourceUniqueViolationException;
import com.annak.handcrafted.mapper.UserMapper;
import com.annak.handcrafted.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;

    @Override
    public UserDto save(UserDto userDto) {
        User user = userMapper.toEntity(userDto);

        if (userRepository.existsByUserName(user.getUsername())) {
            throw new ResourceUniqueViolationException("User with username <%s> already exists!".formatted((user.getUsername())));
        }
        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));
        return userMapper.toDTO(userRepository.save(user));
    }
}
