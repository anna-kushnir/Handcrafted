package com.annak.handcrafted.service;

import com.annak.handcrafted.dto.NewUserDto;
import com.annak.handcrafted.dto.UserDto;

public interface UserService {

    UserDto save(NewUserDto newUserDto);

    String update(UserDto userDto);
}
