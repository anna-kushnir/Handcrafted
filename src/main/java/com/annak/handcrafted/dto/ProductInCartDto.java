package com.annak.handcrafted.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class ProductInCartDto {
    private Long id;
    private Long productId;
    private String productName;
    private BigDecimal cost;
    private Long productQuantity;
    private Long quantityInCart;
    private byte[] photo;
}
