package com.annak.handcrafted.service;

import com.annak.handcrafted.dto.ProductDto;
import com.annak.handcrafted.dto.ProductInCartDto;
import com.annak.handcrafted.entity.ProductInCart;
import com.annak.handcrafted.entity.User;
import com.annak.handcrafted.mapper.ProductInCartMapper;
import com.annak.handcrafted.mapper.ProductMapper;
import com.annak.handcrafted.repository.ProductInCartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductInCartServiceImpl implements ProductInCartService {

    private final ProductInCartRepository productInCartRepository;
    private final ProductInCartMapper productInCartMapper;
    private final ProductMapper productMapper;

    @Override
    public List<ProductInCartDto> getAllByUser(User user) {
        return productInCartRepository.findAllByUser(user)
                .stream()
                .map(productInCartMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public String save(User user, ProductDto productDto) {
        if (productInCartRepository.existsByUserUserNameAndProductId(user.getUsername(), productDto.getId())) {
            productInCartRepository.deleteByUserUserNameAndProductId(user.getUsername(), productDto.getId());
            return "Product deleted from cart";
        }
        if (productDto.isInStock()) {
            ProductInCart productInCart = new ProductInCart();
            productInCart.setProduct(productMapper.toEntity(productDto));
            productInCart.setUser(user);
            productInCart.setQuantityInCart(1L);

            productInCartRepository.save(productInCart);
            return "Product added to cart";
        }
        else {
            return "Product is not in stock!";
        }
    }
}
