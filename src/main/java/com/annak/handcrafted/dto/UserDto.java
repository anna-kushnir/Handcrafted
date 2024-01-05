package com.annak.handcrafted.dto;

import lombok.Data;

@Data
public class UserDto {
    private Long id;
    private String userName;
    private String name;
    private String surname;
    private String password;
    private Long userPhone;
    private boolean active;
}
