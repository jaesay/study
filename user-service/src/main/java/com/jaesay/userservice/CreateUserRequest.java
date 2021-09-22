package com.jaesay.userservice;

import lombok.Data;

@Data
public class CreateUserRequest {
    private String email;
    private String password;
}
