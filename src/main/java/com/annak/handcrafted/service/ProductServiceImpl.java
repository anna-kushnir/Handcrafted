package com.annak.handcrafted.service;

import com.annak.handcrafted.dto.ProductDto;
import com.annak.handcrafted.mapper.ProductMapper;
import com.annak.handcrafted.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
}
