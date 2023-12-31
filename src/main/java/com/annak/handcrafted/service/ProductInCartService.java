package com.annak.handcrafted.service;

import com.annak.handcrafted.dto.ProductDto;
import com.annak.handcrafted.dto.ProductInCartDto;
import com.annak.handcrafted.entity.ProductInCart;
import com.annak.handcrafted.entity.User;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ProductInCartService {

    Optional<ProductInCart> getById(Long id);

    List<ProductInCartDto> getAllByUser(User user);

    String saveOrDeleteIfExists(User user, ProductDto productDto);

    void deleteById(Long id);

    void delete(User user, ProductDto productDto);

    void deleteAllByProductId(Long productId);

    BigDecimal getTotalPriceOfProductsInCart(List<ProductInCartDto> productInCartDtoList);
}
