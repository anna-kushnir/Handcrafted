package com.annak.handcrafted.mapper;

import com.annak.handcrafted.dto.NewOrderDto;
import com.annak.handcrafted.dto.OrderDto;
import com.annak.handcrafted.entity.Order;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    OrderDto toDTO(Order order);

    Order toEntity(OrderDto orderDto);
    Order toEntity(NewOrderDto newOrderDto);
}
