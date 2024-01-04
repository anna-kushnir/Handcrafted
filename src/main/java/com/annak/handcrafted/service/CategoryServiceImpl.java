package com.annak.handcrafted.service;

import com.annak.handcrafted.dto.CategoryDto;
import com.annak.handcrafted.entity.Category;
import com.annak.handcrafted.mapper.CategoryMapper;
import com.annak.handcrafted.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public List<CategoryDto> getAll() {
        return categoryRepository.findAllByOrderByNameAsc()
                .stream()
                .map(categoryMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<CategoryDto> getById(Long id) {
        return categoryRepository.findById(id)
                .map(categoryMapper::toDTO);
    }

    @Override
    public Map<String, Long> getCategoryStatistics() {
        List<Object[]> statisticsList = categoryRepository.getCategoryStatistics();
        Map<String, Long> statisticsMap = new LinkedHashMap<>();

        for (Object[] row : statisticsList) {
            statisticsMap.put((String)row[0], (Long)row[1]);
        }
        return statisticsMap;
    }

    @Override
    @Transactional
    public CategoryDto save(CategoryDto categoryDto) {
        Category category = categoryMapper.toEntity(categoryDto);
        category.setId(categoryRepository.save(category).getId());
        return categoryMapper.toDTO(category);
    }

    @Override
    @Transactional
    public String update(CategoryDto categoryDto) {
        Optional<Category> categoryOptional = categoryRepository.findById(categoryDto.getId());
        if (categoryOptional.isEmpty()) {
            return "No category with id <%s> found!".formatted(categoryDto.getId());
        }
        return "Category with id <%s> was successfully updated".formatted(categoryRepository.save(categoryMapper.toEntity(categoryDto)).getId());
    }

    @Override
    public String deleteById(Long id) {
        categoryRepository.deleteById(id);
        return "Category with id <%s> successfully deleted".formatted(id);
    }
}
