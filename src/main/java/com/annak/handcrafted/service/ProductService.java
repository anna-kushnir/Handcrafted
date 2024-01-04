package com.annak.handcrafted.service;

import com.annak.handcrafted.dto.ProductDto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ProductService {

    Optional<ProductDto> getById(Long id);

    Optional<ProductDto> getNotDeletedById(Long id);

    List<ProductDto> getAllNotDeleted();

    List<ProductDto> getAllNotDeletedByFilter(boolean sortByCost, boolean sortByCostAsc, boolean sortByNewness, boolean sortByNewnessAsc, BigDecimal priceLimitFrom, BigDecimal priceLimitTo);

    List<ProductDto> getAllNotDeletedByCategoryId(Long categoryId);

    List<ProductDto> getAllNotDeletedBySearchLine(String searchLine);

    ProductDto save(ProductDto productDto);

    String update(ProductDto productDto);

    String deleteById(Long productId);
}
