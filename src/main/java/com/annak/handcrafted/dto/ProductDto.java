package com.annak.handcrafted.dto;

import com.annak.handcrafted.entity.Category;
import com.annak.handcrafted.entity.Photo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
    private Long id;
    private String name;
    private String description;
    private String keyWords;
    private BigDecimal price;
    private boolean withDiscount;
    private BigDecimal discountedPrice;
    private boolean inStock;
    private Long quantity;
    private LocalDateTime creationDate;
    private Category category;
    private boolean deleted;
    private String colors;
    private List<Photo> photos = new ArrayList<>();
}
