package com.annak.handcrafted.mapper;

import com.annak.handcrafted.dto.PhotoDto;
import com.annak.handcrafted.entity.Photo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PhotoMapper {

    PhotoDto toDTO(Photo photo);

    Photo toEntity(PhotoDto photoDto);
}
