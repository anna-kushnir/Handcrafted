package com.annak.handcrafted.dto;

import com.annak.handcrafted.entity.Category;
import com.annak.handcrafted.entity.Color;
import com.annak.handcrafted.entity.Photo;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class ProductInCartDto {
    private Long id;
    private String name;
    private BigDecimal cost;
    private Long maxQuantity;
    private Long quantityInCart;
    private Photo photo;
}
