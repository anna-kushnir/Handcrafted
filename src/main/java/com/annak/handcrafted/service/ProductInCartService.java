package com.annak.handcrafted.service;

import com.annak.handcrafted.dto.ProductDto;
import com.annak.handcrafted.dto.ProductInCartDto;
import com.annak.handcrafted.entity.User;

import java.util.List;

public interface ProductInCartService {

    List<ProductInCartDto> getAllByUser(User user);

    String saveOrDeleteIfExists(User user, ProductDto productDto);

    void delete(User user, ProductDto productDto);
}
