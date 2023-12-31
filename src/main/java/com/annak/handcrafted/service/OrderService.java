package com.annak.handcrafted.service;

import com.annak.handcrafted.dto.NewOrderDto;
import com.annak.handcrafted.dto.OrderDto;
import com.annak.handcrafted.dto.ProductInCartDto;
import com.annak.handcrafted.entity.User;

import java.util.List;
import java.util.Optional;

public interface OrderService {

    List<OrderDto> getAllByUser(User user);

    Optional<OrderDto> getById(Long orderId);

    String save(NewOrderDto newOrderDto, List<ProductInCartDto> productInCartDtoList);

    String deleteById(Long orderId);
}
