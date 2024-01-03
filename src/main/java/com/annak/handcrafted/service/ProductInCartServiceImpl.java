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

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductInCartServiceImpl implements ProductInCartService {

    private final ProductInCartRepository productInCartRepository;
    private final ProductInCartMapper productInCartMapper;
    private final ProductMapper productMapper;

    @Override
    public Optional<ProductInCart> getById(Long id) {
        return productInCartRepository.findById(id);
    }

    @Override
    public List<ProductInCartDto> getAllByUser(User user) {
        return productInCartRepository.findAllByUser(user)
                .stream()
                .map(productInCartMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public String saveOrDeleteIfExists(User user, ProductDto productDto) {
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
        return "Product is not in stock!";
    }

    @Override
    public String updateQuantityByUserAndProduct(User user, ProductDto productDto, Long quantity) {
        Optional<ProductInCart> productInCartOptional = productInCartRepository.findByUserAndProductId(user, productDto.getId());
        if (productInCartOptional.isEmpty())
            return "Product is not in cart!";
        ProductInCart productInCart = productInCartOptional.get();
        if (productInCart.getProduct().getQuantity() < quantity)
            return "There are not enough products with id <%s>!".formatted(productInCart.getProduct().getId());
        productInCart.setQuantityInCart(quantity);
        productInCartRepository.save(productInCart);
        return "Quantity of product with id <%s> was successfully updated".formatted(productInCart.getProduct().getId());
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        productInCartRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void delete(User user, ProductDto productDto) {
        if (productInCartRepository.existsByUserUserNameAndProductId(user.getUsername(), productDto.getId())) {
            productInCartRepository.deleteByUserUserNameAndProductId(user.getUsername(), productDto.getId());
        }
    }

    @Override
    @Transactional
    public void deleteAllByProductId(Long productId) {
        productInCartRepository.deleteAllByProductId(productId);
    }

    @Override
    public BigDecimal getTotalPriceOfProductsInCart(List<ProductInCartDto> productInCartDtoList) {
        var totalPrice = BigDecimal.ZERO;
        for (var productInCartDto : productInCartDtoList) {
            totalPrice = totalPrice.add(productInCartDto.getCost().multiply(BigDecimal.valueOf(productInCartDto.getQuantityInCart())));
        }
        return totalPrice;
    }
}
