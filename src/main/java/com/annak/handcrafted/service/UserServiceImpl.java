package com.annak.handcrafted.service;

import com.annak.handcrafted.dto.ProductInCartDto;
import com.annak.handcrafted.dto.UserDto;
import com.annak.handcrafted.entity.Role;
import com.annak.handcrafted.entity.User;
import com.annak.handcrafted.exception.ResourceNotFoundException;
import com.annak.handcrafted.exception.ResourceUniqueViolationException;
import com.annak.handcrafted.mapper.CartProductMapper;
import com.annak.handcrafted.mapper.UserMapper;
import com.annak.handcrafted.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final CartProductMapper cartProductMapper;

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

    @Override
    public List<ProductInCartDto> getProductsInCartByUserId(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return cartProductMapper.toDTO(user.getProductsInCart());
        }
        else {
            throw new ResourceNotFoundException("User with id " + userId + " doesn't exist");
        }
    }
}
