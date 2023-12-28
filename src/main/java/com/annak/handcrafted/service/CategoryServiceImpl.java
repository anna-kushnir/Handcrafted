package com.annak.handcrafted.service;

import com.annak.handcrafted.dto.CategoryDto;
import com.annak.handcrafted.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public List<CategoryDto> getAll() {
        return categoryRepository.findAllByOrderByNameAsc();
    }
}
