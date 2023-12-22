package com.annak.handcrafted.dto;

import com.annak.handcrafted.entity.Product;
import lombok.Data;

@Data
public class PhotoDto {
    private Long id;
    private String name;
    private byte[] data;
    private Product product;
}
