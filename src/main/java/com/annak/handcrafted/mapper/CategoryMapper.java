package com.annak.handcrafted.mapper;

import com.annak.handcrafted.dto.CategoryDto;
import com.annak.handcrafted.entity.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    CategoryDto toDTO(Category category);

    Category toEntity(CategoryDto categoryDto);
}
