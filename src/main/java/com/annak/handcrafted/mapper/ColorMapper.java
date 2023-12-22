package com.annak.handcrafted.mapper;

import com.annak.handcrafted.dto.ColorDto;
import com.annak.handcrafted.entity.Color;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ColorMapper {

    ColorDto toDTO(Color color);

    Color toEntity(ColorDto colorDto);
}
