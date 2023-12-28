package com.annak.handcrafted.dto;

import com.annak.handcrafted.entity.Photo;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class FavoriteProductDto {
    private Long id;
    private String productName;
    private BigDecimal price;
    private boolean withDiscount;
    private BigDecimal discountedPrice;
    private Photo photo;
    private boolean inStock;
}
