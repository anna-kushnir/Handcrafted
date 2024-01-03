package com.annak.handcrafted.mapper;

import com.annak.handcrafted.dto.ProductInOrderDto;
import com.annak.handcrafted.entity.ProductInOrder;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductInOrderMapper {

    ProductInOrderDto toDTO(ProductInOrder productInOrder);

    ProductInOrder toEntity(ProductInOrderDto productInOrderDto);
}
