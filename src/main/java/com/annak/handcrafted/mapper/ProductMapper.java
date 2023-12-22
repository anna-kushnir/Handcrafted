package com.annak.handcrafted.mapper;

import com.annak.handcrafted.dto.ProductDto;
import com.annak.handcrafted.entity.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductDto toDTO(Product product);

    Product toEntity(ProductDto productDto);
}
