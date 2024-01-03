package com.annak.handcrafted.service;

import com.annak.handcrafted.dto.ProductInOrderDto;
import com.annak.handcrafted.entity.Order;
import com.annak.handcrafted.entity.ProductInCart;
import com.annak.handcrafted.entity.ProductInOrder;

import java.util.List;

public interface ProductInOrderService {

    List<ProductInOrder> getAllByOrderId(Long orderId);

    List<ProductInOrderDto> getAllDtosByOrderId(Long orderId);

    void save(Order order, ProductInCart productInCart);

    void save(Order order, ProductInOrderDto productInOrderDto);

    void saveAll(Order order, List<ProductInOrderDto> productInOrderDtoList);

    void returnProductsFromOrderToStockByOrderId(Long orderId);
}
