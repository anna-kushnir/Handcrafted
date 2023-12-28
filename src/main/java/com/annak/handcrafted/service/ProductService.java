package com.annak.handcrafted.service;

import com.annak.handcrafted.dto.ProductDto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ProductService {

    List<ProductDto> getAll();

    Optional<ProductDto> getById(Long id);

    Object getAllFiltered(boolean sortByCost, boolean sortByCostAsc, boolean sortByNewness, boolean sortByNewnessAsc, BigDecimal priceLimitFrom, BigDecimal priceLimitTo);

    ProductDto save(ProductDto productDto);

    ProductDto update(ProductDto productDto);
}
