package com.annak.handcrafted.mapper;

import com.annak.handcrafted.dto.ProductInCartDto;
import com.annak.handcrafted.entity.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CartProductMapper {

    public List<ProductInCartDto> toDTO(Map<Product, Long> productLongMap) {
        List<ProductInCartDto> productInCartDtoList = new ArrayList<>();
        for (Map.Entry<Product, Long> productLongEntry : productLongMap.entrySet()) {
            Product product = productLongEntry.getKey();
            ProductInCartDto productInCartDto = new ProductInCartDto(
                    product.getId(),
                    product.getName(),
                    (product.isWithDiscount() ? product.getDiscountedPrice() : product.getPrice()),
                    product.getQuantity(),
                    productLongEntry.getValue(),
                    (product.getPhotos().isEmpty() ? null : product.getPhotos().get(0)));
            productInCartDtoList.add(productInCartDto);
        }
        return productInCartDtoList;
    }
}
