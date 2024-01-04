package com.annak.handcrafted.service;

import com.annak.handcrafted.dto.CategoryDto;

import java.util.List;
import java.util.Map;

public interface CategoryService {

    List<CategoryDto> getAll();

    Map<String, Long> getCategoryStatistics();
}
