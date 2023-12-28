package com.annak.handcrafted.repository;

import com.annak.handcrafted.dto.CategoryDto;
import com.annak.handcrafted.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<CategoryDto> findAllByOrderByNameAsc();
}
