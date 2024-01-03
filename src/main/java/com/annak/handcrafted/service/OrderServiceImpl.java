package com.annak.handcrafted.service;

import com.annak.handcrafted.dto.*;
import com.annak.handcrafted.entity.*;
import com.annak.handcrafted.entity.embedded.DeliveryAddress;
import com.annak.handcrafted.entity.embedded.Status;
import com.annak.handcrafted.entity.embedded.TypeOfReceipt;
import com.annak.handcrafted.mapper.OrderMapper;
import com.annak.handcrafted.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    private final ProductService productService;
    private final ProductInCartService productInCartService;
    private final ProductInOrderService productInOrderService;


    @Override
    public List<OrderDto> getAllByUser(User user) {
        return orderRepository.findAllByUser(user)
                .stream()
                .map(orderMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderDto> getAllByStatusName(String status_name) {
        return orderRepository.findAllByStatus(Status.valueOf(status_name.toUpperCase()))
                .stream()
                .map(orderMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<OrderDto> getById(Long orderId) {
        return orderRepository.findById(orderId)
                .map(orderMapper::toDTO);
    }

    @Override
    @Transactional
    public String save(NewOrderDto newOrderDto, List<ProductInCartDto> productInCartDtoList) {
        Order order = orderMapper.toEntity(newOrderDto);
        order.setPrice(productInCartService.getTotalPriceOfProductsInCart(productInCartDtoList));
        order.setFormationDate(LocalDateTime.now());
        order.setStatus(Status.IN_PROCESSING);
        if (order.getTypeOfReceipt() == TypeOfReceipt.DELIVERY_TO_THE_POST_OFFICE) {
            order.setDeliveryAddress(new DeliveryAddress(
                    newOrderDto.getDeliveryAddressRegion(),
                    newOrderDto.getDeliveryAddressCity(),
                    newOrderDto.getDeliveryAddressPostAddress()
            ));
        }
        order.setId(orderRepository.save(order).getId());

        for (ProductInCartDto productInCartDto : productInCartDtoList) {
            Optional<ProductDto> productDtoOptional = productService.getNotDeletedById(productInCartDto.getProductId());
            if (productDtoOptional.isPresent()) {
                ProductDto productDto = productDtoOptional.get();
                if (!(productInCartDto.getQuantityInCart() <= productDto.getQuantity())) {
                    return "There are not enough products with id <%s>!".formatted(productDto.getId());
                } else {
                    productInOrderService.save(order, productInCartService.getById(productInCartDto.getId()).get());
                    productInCartService.deleteById(productInCartDto.getId());
                    productDto.setQuantity(productDto.getQuantity() - productInCartDto.getQuantityInCart());
                    productDto.setInStock(productDto.getQuantity() != 0);
                    productService.update(productDto);
                }
            } else {
                return "Product with id <%s> was not found!".formatted(productInCartDto.getProductId());
            }
        }

        return "Order was successfully created with id <%s>".formatted(order.getId());
    }

    @Override
    public String update(OrderDto orderDto, List<ProductInOrderDto> productInOrderDtoList) {
        Order order = orderMapper.toEntity(orderDto);
        orderRepository.save(order);
        productInOrderService.saveAll(order, productInOrderDtoList);
        return "Order with id <%s> was successfully updated".formatted(orderDto.getId());
    }

    @Override
    public String cancel(OrderDto orderDto) {
        if (orderDto.getStatus().getId() == 1) {
            productInOrderService.returnProductsFromOrderToStockByOrderId(orderDto.getId());
            orderRepository.deleteById(orderDto.getId());
            return "Order with id <%s> successfully canceled".formatted(orderDto.getId());
        }
        return "Order with id <%s> has already been accepted, so it cannot be canceled!".formatted(orderDto.getId());
    }
}
