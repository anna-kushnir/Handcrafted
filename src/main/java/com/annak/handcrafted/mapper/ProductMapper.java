package com.annak.handcrafted.mapper;

import com.annak.handcrafted.dto.ProductDto;
import com.annak.handcrafted.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ProductMapper {

    private final ColorInProductMapper colorInProductMapper;

    public ProductDto toDTO(Product product) {
        return new ProductDto(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getKeyWords(),
                product.getPrice(),
                product.isWithDiscount(),
                product.getDiscountedPrice(),
                product.isInStock(),
                product.getQuantity(),
                product.getCreationDate(),
                product.getCategory(),
                colorInProductMapper.colorsToString(product.getColors()),
                product.getPhotos()
        );
    }

    public Product toEntity(ProductDto productDto) {
        return new Product(
                productDto.getId(),
                productDto.getName(),
                productDto.getDescription(),
                productDto.getKeyWords(),
                productDto.getPrice(),
                productDto.isWithDiscount(),
                productDto.getDiscountedPrice(),
                productDto.isInStock(),
                productDto.getQuantity(),
                productDto.getCreationDate(),
                productDto.getCategory(),
                colorInProductMapper.colorsToList(productDto.getColors()),
                productDto.getPhotos()
        );
    }
}
