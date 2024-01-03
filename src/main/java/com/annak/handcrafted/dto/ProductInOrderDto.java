package com.annak.handcrafted.dto;

import com.annak.handcrafted.entity.Order;
import com.annak.handcrafted.entity.Product;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductInOrderDto {
    private Long id;
    private Order order;
    private Product product;
    private BigDecimal productCost;
    private Long quantityInOrder;
}
