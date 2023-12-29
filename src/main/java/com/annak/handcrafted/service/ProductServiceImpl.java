package com.annak.handcrafted.service;

import com.annak.handcrafted.dto.ProductDto;
import com.annak.handcrafted.entity.Product;
import com.annak.handcrafted.exception.ResourceNotFoundException;
import com.annak.handcrafted.mapper.ProductMapper;
import com.annak.handcrafted.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    public List<ProductDto> getAll() {
        return productRepository.findAll()
                .stream()
                .map(productMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<ProductDto> getById(Long id) {
        return productRepository.findById(id)
                .map(productMapper::toDTO);
    }

    @Override
    public List<ProductDto> getAllFiltered(boolean sortByCost, boolean sortByCostAsc, boolean sortByNewness, boolean sortByNewnessAsc, BigDecimal priceLimitFrom, BigDecimal priceLimitTo) {
        List<Product> productList;
        if (!sortByCost && !sortByNewness) {
            productList = productRepository.findAllByPriceBetween(priceLimitFrom, priceLimitTo);
        }
        else if (!sortByNewness) {
            if (sortByCostAsc) {
                productList = productRepository.findAllByPriceBetweenOrderByPriceAsc(priceLimitFrom, priceLimitTo);
            }
            else {
                productList = productRepository.findAllByPriceBetweenOrderByPriceDesc(priceLimitFrom, priceLimitTo);
            }
        }
        else if (!sortByCost) {
            if (sortByNewnessAsc) {
                productList = productRepository.findAllByPriceBetweenOrderByCreationDateAsc(priceLimitFrom, priceLimitTo);
            }
            else {
                productList = productRepository.findAllByPriceBetweenOrderByCreationDateDesc(priceLimitFrom, priceLimitTo);
            }
        }
        else {
            if (sortByCostAsc && sortByNewnessAsc) {
                productList = productRepository.findAllByPriceBetweenOrderByPriceAscCreationDateAsc(priceLimitFrom, priceLimitTo);
            }
            else if (sortByCostAsc) {
                productList = productRepository.findAllByPriceBetweenOrderByPriceAscCreationDateDesc(priceLimitFrom, priceLimitTo);
            }
            else if (sortByNewnessAsc) {
                productList = productRepository.findAllByPriceBetweenOrderByPriceDescCreationDateAsc(priceLimitFrom, priceLimitTo);
            }
            else {
                productList = productRepository.findAllByPriceBetweenOrderByPriceDescCreationDateDesc(priceLimitFrom, priceLimitTo);
            }
        }
        return productList.stream()
                .map(productMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ProductDto save(ProductDto productDto) {
        productDto.setCreationDate(LocalDateTime.now());
        Product product = productMapper.toEntity(productDto);
        product.setWithDiscount(false);
        if (!product.isInStock()) {
            product.setQuantity(0L);
        }
        else if (product.getQuantity() == 0L) {
            product.setInStock(false);
        }
        product.setId(productRepository.save(product).getId());
        return productMapper.toDTO(product);
    }

    @Override
    @Transactional
    public ProductDto update(ProductDto productDto) {
        if (!productRepository.existsById(productDto.getId())) {
            throw new ResourceNotFoundException("No product with id <%s> found!".formatted((productDto.getId())));
        }
        Product product = productMapper.toEntity(productDto);
        product.setCreationDate(productRepository.findById(productDto.getId()).get().getCreationDate());
        if (!product.isInStock()) {
            product.setQuantity(0L);
        }
        else if (product.getQuantity() == 0L) {
            product.setInStock(false);
        }
        productRepository.save(product);
        return productMapper.toDTO(product);
    }

    @Override
    @Transactional
    public String deleteById(Long productId) {
        if (productRepository.existsById(productId)) {
            productRepository.deleteById(productId);
            return "Product with id <%s> successfully deleted".formatted(productId);
        }
        return "No product with id <%s> found!".formatted(productId);
    }
}
