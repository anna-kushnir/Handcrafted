package com.annak.handcrafted.service;

import com.annak.handcrafted.dto.FavoriteProductDto;
import com.annak.handcrafted.dto.ProductDto;
import com.annak.handcrafted.entity.FavoriteProduct;
import com.annak.handcrafted.entity.User;
import com.annak.handcrafted.mapper.FavoriteProductMapper;
import com.annak.handcrafted.mapper.ProductMapper;
import com.annak.handcrafted.repository.FavoriteProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FavoriteProductServiceImpl implements FavoriteProductService {

    private final FavoriteProductRepository favoriteProductRepository;
    private final FavoriteProductMapper favoriteProductMapper;

    private final ProductMapper productMapper;

    @Override
    public List<FavoriteProductDto> getAllByUser(User user) {
        return favoriteProductRepository.findAllByUser(user)
                .stream()
                .map(favoriteProductMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void save(User user, ProductDto productDto) {
        if (!favoriteProductRepository.existsByUserUserNameAndProductId(user.getUsername(), productDto.getId())) {
            FavoriteProduct favoriteProduct = new FavoriteProduct();
            favoriteProduct.setProduct(productMapper.toEntity(productDto));
            favoriteProduct.setUser(user);

            favoriteProductRepository.save(favoriteProduct);
        }
    }

    @Override
    @Transactional
    public String saveOrDeleteIfExists(User user, ProductDto productDto) {
        if (favoriteProductRepository.existsByUserUserNameAndProductId(user.getUsername(), productDto.getId())) {
            favoriteProductRepository.deleteByUserUserNameAndProductId(user.getUsername(), productDto.getId());
            return "Product deleted from favorites";
        }
        else {
            FavoriteProduct favoriteProduct = new FavoriteProduct();
            favoriteProduct.setProduct(productMapper.toEntity(productDto));
            favoriteProduct.setUser(user);

            favoriteProductRepository.save(favoriteProduct);
            return "Product added to favorites";
        }
    }

    @Override
    public void deleteAllByProductId(Long productId) {
        favoriteProductRepository.deleteAllByProductId(productId);
    }
}
