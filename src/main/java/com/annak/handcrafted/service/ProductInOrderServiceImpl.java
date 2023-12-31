package com.annak.handcrafted.service;

import com.annak.handcrafted.entity.Order;
import com.annak.handcrafted.entity.Product;
import com.annak.handcrafted.entity.ProductInCart;
import com.annak.handcrafted.entity.ProductInOrder;
import com.annak.handcrafted.repository.ProductInOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductInOrderServiceImpl implements ProductInOrderService {

    private final ProductInOrderRepository productInOrderRepository;

    @Override
    public List<ProductInOrder> getAllByOrderId(Long orderId) {
        return productInOrderRepository.findAllByOrderId(orderId);
    }

    @Override
    @Transactional
    public void save(Order order, ProductInCart productInCart) {
        var productInOrder = new ProductInOrder();
        productInOrder.setOrder(order);
        Product product = productInCart.getProduct();
        productInOrder.setProduct(product);
        productInOrder.setProductCost(
                product.isWithDiscount() ? product.getDiscountedPrice() : product.getPrice());
        productInOrder.setQuantityInOrder(productInCart.getQuantityInCart());
        productInOrderRepository.save(productInOrder);
    }

    @Override
    @Transactional
    public void saveAll(Order order, List<ProductInCart> productsInCart) {
        productsInCart.forEach(productInCart -> save(order, productInCart));
    }
}
