package com.annak.handcrafted.dto;

import com.annak.handcrafted.entity.Category;
import com.annak.handcrafted.entity.Color;
import com.annak.handcrafted.entity.Photo;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
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
    private List<Color> colors = new ArrayList<>();
    private List<Photo> photos = new ArrayList<>();

    public String getColorsString() {
        StringBuilder result = new StringBuilder();
        if (colors.isEmpty()) {
            return "";
        }
        for (Color color : colors) {
            result.append(color.getName()).append(", ");
        }
        return result.delete(result.length() - 2, result.length()).toString();
    }
}
