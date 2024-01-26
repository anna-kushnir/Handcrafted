package com.annak.handcrafted.mapper;

import com.annak.handcrafted.dto.FavoriteProductDto;
import com.annak.handcrafted.entity.FavoriteProduct;
import com.annak.handcrafted.entity.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public class FavoriteProductMapper {

    public FavoriteProductDto toDTO(FavoriteProduct favoriteProduct) {
        Product product = favoriteProduct.getProduct();
        return new FavoriteProductDto(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.isWithDiscount(),
                product.getDiscountedPrice(),
                product.getPhoto(),
                product.isInStock());
    }
}
