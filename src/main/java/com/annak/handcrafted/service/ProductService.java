package com.annak.handcrafted.service;

import com.annak.handcrafted.dto.ProductDto;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    List<ProductDto> getAll();

    Optional<ProductDto> getById(Long id);
}
