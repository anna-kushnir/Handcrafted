package com.annak.handcrafted.mapper;

import com.annak.handcrafted.dto.ProductInCartDto;
import com.annak.handcrafted.entity.Product;
import org.mapstruct.Mapper;

import java.util.Map;

@Mapper(componentModel = "spring")
public class CartProductMapper {

    public ProductInCartDto toDTO(Map.Entry<Product, Long> productLongEntry) {
        Product product = productLongEntry.getKey();
        return new ProductInCartDto(
                product.getId(),
                product.getName(),
                (product.isWithDiscount() ? product.getDiscountedPrice() : product.getPrice()),
                product.getQuantity(),
                productLongEntry.getValue(),
                (product.getPhotos().isEmpty() ? null : product.getPhotos().get(0)));
    }
}
