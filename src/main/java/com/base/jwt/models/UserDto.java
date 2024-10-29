package com.base.jwt.models;

import lombok.Data;

import java.util.List;

@Data
public class UserDto {

    private String email;

    private String password;

    private String fullName;

    private List<Role> roles;
}
