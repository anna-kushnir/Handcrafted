package com.annak.handcrafted.service;

import com.annak.handcrafted.dto.ProductInOrderDto;
import com.annak.handcrafted.entity.Order;
import com.annak.handcrafted.entity.Product;
import com.annak.handcrafted.entity.ProductInCart;
import com.annak.handcrafted.entity.ProductInOrder;
import com.annak.handcrafted.mapper.ProductInOrderMapper;
import com.annak.handcrafted.mapper.ProductMapper;
import com.annak.handcrafted.repository.ProductInOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductInOrderServiceImpl implements ProductInOrderService {

    private final ProductInOrderRepository productInOrderRepository;
    private final ProductInOrderMapper productInOrderMapper;

    private final ProductService productService;
    private final ProductMapper productMapper;

    @Override
    public List<ProductInOrder> getAllByOrderId(Long orderId) {
        return productInOrderRepository.findAllByOrderId(orderId);
    }

    @Override
    public List<ProductInOrderDto> getAllDtosByOrderId(Long orderId) {
        return productInOrderRepository.findAllByOrderId(orderId)
                .stream()
                .map(productInOrderMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void save(Order order, ProductInCart productInCart) {
        var productInOrder = new ProductInOrder();
        productInOrder.setOrder(order);
        Product product = productInCart.getProduct();
        productInOrder.setProduct(product);
        productInOrder.setProductCost(
                product.isWithDiscount() ? product.getDiscountedPrice() : product.getPrice());
        productInOrder.setQuantityInOrder(productInCart.getQuantityInCart());
        productInOrderRepository.save(productInOrder);
    }

    @Override
    @Transactional
    public void save(Order order, ProductInOrderDto productInOrderDto) {
        productInOrderDto.setId(null);
        productInOrderDto.setOrder(order);
        productInOrderRepository.save(productInOrderMapper.toEntity(productInOrderDto));
    }

    @Override
    @Transactional
    public void saveAll(Order order, List<ProductInOrderDto> productInOrderDtoList) {
        productInOrderDtoList.forEach(productInOrderDto -> save(order, productInOrderDto));
    }

    @Override
    public void returnProductsFromOrderToStockByOrderId(Long orderId) {
        List<ProductInOrder> productInOrderList = getAllByOrderId(orderId);
        for (ProductInOrder productInOrder : productInOrderList) {
            Product product = productInOrder.getProduct();
            product.setQuantity(product.getQuantity() + productInOrder.getQuantityInOrder());
            product.setInStock(true);
            productService.update(productMapper.toDTO(product));
            productInOrderRepository.delete(productInOrder);
        }
    }
}
