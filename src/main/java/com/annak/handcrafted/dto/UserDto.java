package com.annak.handcrafted.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserDto {
    @NotBlank
    @Size(min=8, max=20)
    private String userName;
    @NotBlank
    @Size(min=2, max=20)
    private String name;
    @NotBlank
    @Size(min=2, max=20)
    private String surname;
    @NotBlank
    @Size(min=6, max=12)
    private String password;
}
