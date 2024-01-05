package com.annak.handcrafted.service;

import com.annak.handcrafted.dto.ProductDto;
import com.annak.handcrafted.entity.Product;
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

    private final ProductInCartService productInCartService;
    private final FavoriteProductService favoriteProductService;

    @Override
    public Optional<ProductDto> getById(Long id) {
        return productRepository.findById(id)
                .map(productMapper::toDTO);
    }

    @Override
    public Optional<ProductDto> getNotDeletedById(Long id) {
        return productRepository.findByIdAndDeletedIsFalse(id)
                .map(productMapper::toDTO);
    }

    @Override
    public List<ProductDto> getAllNotDeleted() {
        return productRepository.findAllByDeletedIsFalse()
                .stream()
                .map(productMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductDto> getAllNotDeletedByFilter(boolean sortByCost, boolean sortByCostAsc, boolean sortByNewness, boolean sortByNewnessAsc, BigDecimal priceLimitFrom, BigDecimal priceLimitTo) {
        List<Product> productList;
        if (!sortByCost && !sortByNewness) {
            productList = productRepository.findAllByPriceBetweenAndDeletedIsFalse(priceLimitFrom, priceLimitTo);
        }
        else if (!sortByNewness) {
            if (sortByCostAsc) {
                productList = productRepository.findAllByPriceBetweenAndDeletedIsFalseOrderByPriceAsc(priceLimitFrom, priceLimitTo);
            }
            else {
                productList = productRepository.findAllByPriceBetweenAndDeletedIsFalseOrderByPriceDesc(priceLimitFrom, priceLimitTo);
            }
        }
        else if (!sortByCost) {
            if (sortByNewnessAsc) {
                productList = productRepository.findAllByPriceBetweenAndDeletedIsFalseOrderByCreationDateAsc(priceLimitFrom, priceLimitTo);
            }
            else {
                productList = productRepository.findAllByPriceBetweenAndDeletedIsFalseOrderByCreationDateDesc(priceLimitFrom, priceLimitTo);
            }
        }
        else {
            if (sortByCostAsc && sortByNewnessAsc) {
                productList = productRepository.findAllByPriceBetweenAndDeletedIsFalseOrderByPriceAscCreationDateAsc(priceLimitFrom, priceLimitTo);
            }
            else if (sortByCostAsc) {
                productList = productRepository.findAllByPriceBetweenAndDeletedIsFalseOrderByPriceAscCreationDateDesc(priceLimitFrom, priceLimitTo);
            }
            else if (sortByNewnessAsc) {
                productList = productRepository.findAllByPriceBetweenAndDeletedIsFalseOrderByPriceDescCreationDateAsc(priceLimitFrom, priceLimitTo);
            }
            else {
                productList = productRepository.findAllByPriceBetweenAndDeletedIsFalseOrderByPriceDescCreationDateDesc(priceLimitFrom, priceLimitTo);
            }
        }
        return productList.stream()
                .map(productMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductDto> getAllNotDeletedByCategoryId(Long categoryId) {
        return productRepository.findAllByCategoryIdAndDeletedIsFalse(categoryId)
                .stream()
                .map(productMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductDto> getAllNotDeletedBySearchLine(String searchLine) {
        return productRepository.searchProductsBySearchLineAndDeletedIsFalse(searchLine)
                .stream()
                .map(productMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ProductDto save(ProductDto productDto) {
        Product product = productMapper.toEntity(productDto);
        product.setCreationDate(LocalDateTime.now());
        product.setWithDiscount(false);
        product.setDeleted(false);
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
    public String update(ProductDto productDto) {
        Optional<Product> productOptional = productRepository.findByIdAndDeletedIsFalse(productDto.getId());
        if (productOptional.isEmpty())
            return "No product with id <%s> found!".formatted(productDto.getId());
        Product product = productMapper.toEntity(productDto);
        product.setCreationDate(productOptional.get().getCreationDate());
        product.setDeleted(false);
        if (!product.isInStock()) {
            product.setQuantity(0L);
        }
        else if (product.getQuantity() == 0L) {
            product.setInStock(false);
        }
        return "Product with id <%s> was successfully updated".formatted(productRepository.save(product).getId());
    }

    @Override
    @Transactional
    public String deleteById(Long id) {
        Optional<Product> productOptional = productRepository.findByIdAndDeletedIsFalse(id);
        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            product.setDeleted(true);
            productInCartService.deleteAllByProductId(id);
            favoriteProductService.deleteAllByProductId(id);
            productRepository.save(product);
            return "Product with id <%s> successfully deleted".formatted(id);
        }
        return "No product with id <%s> found!".formatted(id);
    }
}
