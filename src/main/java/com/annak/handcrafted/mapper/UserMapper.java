package com.annak.handcrafted.mapper;

import com.annak.handcrafted.dto.NewUserDto;
import com.annak.handcrafted.dto.UserDto;
import com.annak.handcrafted.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto toDTO(User user);

    User toEntity(NewUserDto newUserDto);
    User toEntity(UserDto userDto);
}
