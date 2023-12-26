package com.annak.handcrafted.service;

import com.annak.handcrafted.dto.ProductInCartDto;
import com.annak.handcrafted.dto.UserDto;

import java.util.List;

public interface UserService {

    UserDto save(UserDto userDto);

    List<ProductInCartDto> getProductsInCartByUserId(Long userId);
}
