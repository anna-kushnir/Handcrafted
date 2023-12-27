package com.annak.handcrafted.service;

import com.annak.handcrafted.dto.ProductDto;
import com.annak.handcrafted.dto.ProductInCartDto;
import com.annak.handcrafted.dto.UserDto;
import com.annak.handcrafted.entity.Product;
import com.annak.handcrafted.entity.Role;
import com.annak.handcrafted.entity.User;
import com.annak.handcrafted.exception.ResourceNotFoundException;
import com.annak.handcrafted.exception.ResourceUniqueViolationException;
import com.annak.handcrafted.mapper.CartProductMapper;
import com.annak.handcrafted.mapper.ProductMapper;
import com.annak.handcrafted.mapper.UserMapper;
import com.annak.handcrafted.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final CartProductMapper cartProductMapper;
    private final ProductMapper productMapper;

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
            Map<Product, Long> productLongMap = user.getProductsInCart();
            List<ProductInCartDto> productInCartDtoList = new ArrayList<>();
            for (Map.Entry<Product, Long> productLongEntry : productLongMap.entrySet()) {
                productInCartDtoList.add(cartProductMapper.toDTO(productLongEntry));
            }
            return productInCartDtoList;
        }
        else {
            throw new ResourceNotFoundException("User with id " + userId + " does not exist");
        }
    }

    @Override
    public void addProductToCartByUserId(Long userId, ProductDto productDto) {
        if (productDto.isInStock()) {
            Optional<User> userOptional = userRepository.findById(userId);
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                user.addProductToCart(productMapper.toEntity(productDto));
            }
            else {
                throw new ResourceNotFoundException("User with id " + userId + " does not exist");
            }
        }
        else {
            throw new ResourceNotFoundException("Product with id " + productDto.getId() + " is not in stock");
        }
    }
}
