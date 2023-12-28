package com.annak.handcrafted.service;

import com.annak.handcrafted.dto.FavoriteProductDto;
import com.annak.handcrafted.dto.ProductDto;
import com.annak.handcrafted.entity.User;

import java.util.List;

public interface FavoriteProductService {

    List<FavoriteProductDto> getAllByUser(User user);

    void save(User user, ProductDto productDto);

    String saveOrDeleteIfExists(User user, ProductDto productDto);
}
