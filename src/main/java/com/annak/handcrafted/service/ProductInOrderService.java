package com.annak.handcrafted.service;

import com.annak.handcrafted.entity.Order;
import com.annak.handcrafted.entity.ProductInCart;
import com.annak.handcrafted.entity.ProductInOrder;

import java.util.List;

public interface ProductInOrderService {

    List<ProductInOrder> getAllByOrder(Order order) ;

    void save(Order order, ProductInCart productInCart);

    void saveAll(Order order, List<ProductInCart> productsInCart);
}
