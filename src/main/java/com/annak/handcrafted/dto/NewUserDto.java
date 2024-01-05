package com.annak.handcrafted.dto;

import lombok.Data;

@Data
public class NewUserDto {
    private String userName;
    private String name;
    private String surname;
    private String password;
    private String submitPassword;
}
