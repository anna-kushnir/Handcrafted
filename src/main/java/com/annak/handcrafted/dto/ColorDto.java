package com.annak.handcrafted.dto;

import com.annak.handcrafted.entity.Product;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ColorDto {
    private Long id;
    private String name;
    private List<Product> productsWithColor = new ArrayList<>();
}
