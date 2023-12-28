package com.annak.handcrafted.mapper;

import com.annak.handcrafted.dto.ProductInCartDto;
import com.annak.handcrafted.entity.Product;
import com.annak.handcrafted.entity.ProductInCart;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public class ProductInCartMapper {

    public ProductInCartDto toDTO(ProductInCart productInCart) {
        Product product = productInCart.getProduct();
        return new ProductInCartDto(
                product.getId(),
                product.getName(),
                (product.isWithDiscount() ? product.getDiscountedPrice() : product.getPrice()),
                product.getQuantity(),
                productInCart.getQuantityInCart(),
                (product.getPhotos().isEmpty() ? null : product.getPhotos().get(0)));
    }
}
