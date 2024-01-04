package com.annak.handcrafted.service;

import com.annak.handcrafted.dto.CategoryDto;
import com.annak.handcrafted.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public List<CategoryDto> getAll() {
        return categoryRepository.findAllByOrderByNameAsc();
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
}
